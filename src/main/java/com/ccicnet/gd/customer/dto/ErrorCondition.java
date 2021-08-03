package com.ccicnet.gd.customer.dto;

import com.ccicnet.gd.common.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.regex.Pattern;

@Setter
@Getter
public class ErrorCondition {
    private Set<String> exceptions;  //只针对这些异常进行重试，支持正则表达式，用完整的包含包名的类名匹配表达式。不填则默认对所有异常重试
    private Set<String> errorCodes;  //只针对这些错误码重试（只适用于BusinessException及其继承类），支持正则表达式。不填则默认对所有错误码重试
    private Set<String> errorMessages;//只针对这些错误消息重试（只适用于非BusinessException），支持正则表达式。不填则默认对所有错误消息重试

    /**
     * 检查异常信息是否匹配任务参数里面的正则表达式。如果表达式列表为空，则不需要检查，直接匹配。
     */
    public boolean matches(Exception e) {
        if (CollectionUtils.isEmpty(exceptions)) {
            return true;
        }

        Throwable cause = ExceptionUtils.getRootCause(e);
        if (cause == null) {
            cause = e;
        }
        String className = cause.getClass().getCanonicalName();

        for (String retryException : exceptions) {
            if (!Pattern.matches(retryException, className)) {
                continue;
            }

            if (e instanceof BusinessException) {
                if (CollectionUtils.isEmpty(errorCodes)) {
                    return true;
                }

                String errorCode = ((BusinessException) e).getType().getCode();
                for (String retryErrorCode : errorCodes) {
                    if (Pattern.matches(retryErrorCode, errorCode))
                        return true;
                }
            } else {
                if (CollectionUtils.isEmpty(errorMessages)) {
                    return true;
                }

                for (String errorMessage : errorMessages) {
                    if (Pattern.matches(errorMessage, e.getMessage()))
                        return true;
                }
            }
        }

        return false;
    }
}