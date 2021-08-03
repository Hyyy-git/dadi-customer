package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 账户注册 返回报文
 */
@Getter
@Setter
@AllArgsConstructor
public class AccountRegisterRes extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    private Long accountId;
    private Boolean isNew;
}
