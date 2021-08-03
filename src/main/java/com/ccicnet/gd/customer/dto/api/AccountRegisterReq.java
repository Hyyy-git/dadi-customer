package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Request;
import lombok.Getter;
import lombok.Setter;

/**
 * 账户注册 请求报文
 */
@Getter
@Setter
public class AccountRegisterReq extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    private String phoneNo;
    private String channel;
}
