package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Request;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取客户ID 请求报文
 */
@Getter
@Setter
public class CustomerRegisterReq extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    /**
     * 身份证
     */
    private String certId;

    /**
     * 注册手机号码
     */
    private String phoneNo;
}
