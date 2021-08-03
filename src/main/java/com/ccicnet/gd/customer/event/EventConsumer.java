package com.ccicnet.gd.customer.event;

import com.ccicnet.gd.common.environment.log.LogContext;
import com.ccicnet.gd.customer.constant.common.KafkaTopic;
import com.ccicnet.gd.customer.dto.api.CustomerUpdateReq;
import com.ccicnet.gd.customer.service.CustomerUpdateService;
import com.ccicnet.gd.customer.util.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EventConsumer {
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private CustomerUpdateService updateService;

    @Resource
    private GlobalExceptionHandler exceptionHandler;

    @LogContext
    @KafkaListener(topics = {KafkaTopic.BUSI_CUSTOMER_UPDATE})
    public void updateCustomer(String message) {
        log.info("received:{}", message);

        try {
            CustomerUpdateReq req = objectMapper.readValue(message, CustomerUpdateReq.class);
            if (req.getCustomerId() == null) {
                log.warn("no customerId in the message.");
                return;
            }
            updateService.update(req);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }

        log.info("updateCustomer done.");
    }
}
