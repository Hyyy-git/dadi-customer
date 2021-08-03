package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.customer.dto.modal.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 查询客户信息 返回报文
 */
@Getter
@Setter
public class CustomerQueryRes extends Dto {
    private static final long serialVersionUID = 1L;

    private String certId;
    private String name;

    private CustomerBasicDto basic;
    private List<? extends CustomerBankCardDto> bankCards;
    private List<? extends CustomerAttachDto> attachs;
    private List<? extends CustomerContactDto> contacts;
    private List<? extends CustomerCareerDto> careers;
    private List<? extends CustomerVehicleDto> vehicles;
    private List<? extends CustomerHouseDto> houses;
    private List<? extends CustomerResidenceDto> residences;
    private List<? extends CustomerLifeInsuranceDto> lifeInsurances;
}
