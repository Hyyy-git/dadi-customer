package com.ccicnet.gd.customer.util;

import com.ccicnet.gd.customer.event.EventProducer;
import com.ccicnet.gd.customer.service.config.ConfigCache;
import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.dto.RespRoot;
import com.ccicnet.gd.common.dto.Response;
import com.ccicnet.gd.common.exception.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Set;
import java.util.regex.Pattern;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

 @Resource
    private EventProducer eventProducer;

    /**
     * 满足这些条件的异常，需要发送到业务告警平台。
     */
    @Getter
    @Setter
    public static class AlarmCondition extends Dto {
        private static final long serialVersionUID = 1L;
        private Set<String> exceptions;
        private Set<String> errorCodes;
        private Set<String> errorMessages;
    }

    @ExceptionHandler
    @ResponseBody
    public RespRoot handle(Exception e) {
        Response<Object> response = new Response<>();

        ConfigCache.ALARM_CONFIGS.forEach(config -> {
            AlarmCondition alarmConfigs = Dto.fromJson(config.getConfigValue(), AlarmCondition.class);
            if (matches(alarmConfigs, e)) {
                eventProducer.sendWarnMessage(config.getConfigKey(), e);
            }
        });
        if (e instanceof IdempotenceException) {// 幂等性的异常，如果有数据则直接返回。否则和普通的BusinessException一样处理。
            IdempotenceException ie = (IdempotenceException) e;
            Object data = ie.getData();
            log.info("幂等返回");
            response.setResultType(ie.getType());
            response.setData(data);
            return response;
        }

        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            logException(be);
            setResultType(response, be);
        } else if (e instanceof MethodArgumentNotValidException) {
            log.warn("Request valid error. {}", e.getMessage(), e);
            StringBuffer sbErr = new StringBuffer();
            // 获取错误信息
            BindingResult errors = ((MethodArgumentNotValidException) e).getBindingResult();
            errors.getFieldErrors().forEach(error -> {
                String s = error.getDefaultMessage();
                if (sbErr.length() > 0) {
                    sbErr.append(",");
                }
                sbErr.append(s);
            });
            response.setResultType(CommonResult.ILLEGAL_ARGUMENT, sbErr.toString());

        } else if (isPreconditionException(e)) {
            log.warn("Request error. {}", e.getMessage(), e);
            response.setResultType(CommonResult.ILLEGAL_ARGUMENT, e.getMessage());
        } else {
            log.error("System error. {}", e.getMessage(), e);
            response.setResultType(CommonResult.PROCESSING);
        }

        return response;
    }

    private void setResultType(RespRoot resp, BusinessException be) {
        ResultType type = be.getType();
        if (type instanceof CommonResult) {
            resp.setResultType(type, be.getArgs());
        } else {
            resp.setResultType(new ImmediateResult(type.getCode(), be.getMessage(), type.getLogLevel()));
        }
    }

    /**
     * 对参数校验失败做特殊处理，它和 BusinessException 一样，都是业务失败。
     */
    public static boolean isPreconditionException(Exception e) {
        return e instanceof IllegalArgumentException || e instanceof NullPointerException
                || e instanceof IndexOutOfBoundsException;
    }

    public static void logException(BusinessException e) {
        if (CommonResult.SUCCESS.equals(e.getType())) { // 幂等抛出的成功
            return;
        }

        switch (e.getType().getLogLevel()) {
            case DEBUG:
                log.debug("error code={}, message={}", e.getType().getCode(), e.getMessage(), e);
                break;

            case INFO:
                log.info("error code={}, message={}", e.getType().getCode(), e.getMessage(), e);
                break;

            case WARN:
                log.warn("error code={}, message={}", e.getType().getCode(), e.getMessage(), e);
                break;

            default:
                log.error("error code={}, message={}", e.getType().getCode(), e.getMessage(), e);
                break;
        }
    }


    /**
     * 检查异常信息是否匹配任务参数里面的正则表达式。如果表达式列表为空，则不需要检查，直接匹配。
     */
    private static boolean matches(AlarmCondition alarmCondition, Exception e) {

        Throwable cause = ExceptionUtils.getRootCause(e);
        if (cause == null) {
            cause = e;
        }
        String className = cause.getClass().getCanonicalName();

        if (!CollectionUtils.isEmpty(alarmCondition.exceptions)) {
            for (String regex : alarmCondition.exceptions) {
                if (Pattern.matches(regex, className)) {
                    return true;
                }
            }
        }
        if (!CollectionUtils.isEmpty(alarmCondition.errorCodes)) {
            if (e instanceof BusinessException) {
                String errorCode = ((BusinessException) e).getType().getCode();
                for (String regex : alarmCondition.errorCodes) {
                    if (Pattern.matches(regex, errorCode)) {
                        return true;
                    }
                }
            }
        }

        if (!CollectionUtils.isEmpty(alarmCondition.errorMessages)) {
            for (String regex : alarmCondition.errorMessages) {
                if (Pattern.matches(regex, e.getMessage())) {
                    return true;
                }
            }
        }

        return false;
    }
}