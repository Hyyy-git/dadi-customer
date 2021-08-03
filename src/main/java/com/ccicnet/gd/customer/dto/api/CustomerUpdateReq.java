package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Request;
import com.ccicnet.gd.customer.dto.modal.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新客户信息
 */
@Getter
@Setter
public class CustomerUpdateReq extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    private String applyNo;
    private Long customerId;

    private CustomerBasicDto basic;
    private CustomerBankCardDto[] bankCards;
    private CustomerAttachDto[] attachs;
    private CustomerContactDto[] contacts;
    private CustomerCareerDto[] careers;
    private CustomerVehicleDto[] vehicles;
    private CustomerHouseDto[] houses;
    private CustomerResidenceDto[] residences;
    private CustomerLifeInsuranceDto[] lifeInsurances;
}
