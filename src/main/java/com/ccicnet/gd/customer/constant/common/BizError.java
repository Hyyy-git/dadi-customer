package com.ccicnet.gd.customer.constant.common;

import com.ccicnet.gd.common.exception.LogLevel;
import com.ccicnet.gd.common.exception.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizError implements ResultType {
    DATA_NOT_FOUND("320001", "找不到数据 {}"),
    CONFIG_ERROR("320002", "找不到配置或配置数据错误,catalog={},cond={}", LogLevel.ERROR),
    FUNCTION_NOT_PROVIDED("320003", "{}功能暂未开放"),

    CUSTOMER_NOT_FOUND("320100", "客户不存在"),

    ACCOUNT_NOT_FOUND("320200", "账户不存在"),
    ACCOUNT_CLOSED("320201", "账户已注销"),
    REALNAME_CANNOT_NOT_CLOSE("320202", "实名客户不能APP销户，请联系客服"),

    SYSTEM_ERROR("329999", "系统错误，请联系管理员", LogLevel.ERROR);

    private final String code;
    private final String message;
    private final LogLevel logLevel;

    BizError(String code, String message) {
        this.code = code;
        this.message = message;
        this.logLevel = LogLevel.WARN;
    }
}
