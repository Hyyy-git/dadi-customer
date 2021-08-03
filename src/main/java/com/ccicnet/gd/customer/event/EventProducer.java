package com.ccicnet.gd.customer.event;

import com.ccicnet.gd.common.constant.SystemModule;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.KafkaTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class EventProducer {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, Object object, Object busiId) {
        String value = object.toString();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, value);

        SuccessCallback<SendResult<String, String>> successCallback = result -> {
            log.info("send success, partition={}, offset={}, busiId={}",
                    result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), //打印partition和offset，以便查找问题时唯一定位kafka的消息
                    value);////完整的报文可能太大，建议打印业务主键，比如applyNo
        };

        FailureCallback failureCallback = ex -> {
            log.error("send fail, busiId={}", busiId, ex);
        };

        future.addCallback(successCallback, failureCallback);
    }

    public void customerIdChange(String phoneNo, Long oldCustomerId, Long newCustomerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("phoneNo", phoneNo);
        paramMap.put("oldCustomerId", oldCustomerId);
        paramMap.put("newCustomerId", newCustomerId);

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("systemId", SystemModule.KHGL.getCode());
        messageMap.put("templateId", "GDCUSTOMER_ID_CHANGE");
        messageMap.put("params", paramMap);
        this.send(KafkaTopic.BUSI_SYS_ALARM, messageMap, UUID.randomUUID().toString());
    }

    public void sendWarnMessage(String sendTypes, Exception e) {
        Map<String, Object> paramMap = new HashMap<>();
        Throwable cause = ExceptionUtils.getRootCause(e);
        paramMap.put("cause", cause != null && cause != e ? cause.getClass().getCanonicalName() : "");
        paramMap.put("class", e.getClass().getCanonicalName());
        paramMap.put("code", e instanceof BusinessException ? ((BusinessException) e).getType().getCode() : "");
        paramMap.put("message", e.getMessage());

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("systemId", SystemModule.KHGL.getCode());
        messageMap.put("templateId", "GDCUSTOMER_ERROR_ALARM");
        messageMap.put("sendTypes", sendTypes.split(","));
        messageMap.put("params", paramMap);
        // 发送kafka信息到告警
        this.send(KafkaTopic.BUSI_SYS_ALARM, messageMap, UUID.randomUUID().toString());
    }
}