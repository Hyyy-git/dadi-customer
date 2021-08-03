package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Request;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询客户信息
 */
@Getter
@Setter
public class CustomerQueryReq extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    private Long customerId;
    private String applyNo;

    private Boolean basic = false;
    private Boolean attachs = false;
    private Boolean bankCards = false;
    private Boolean careers = false;
    private Boolean contacts = false;
    private Boolean vehicles = false;
    private Boolean houses = false;
    private Boolean residences = false;
    private Boolean lifeInsurances = false;
}
