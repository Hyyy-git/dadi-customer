package com.ccicnet.gd.customer.constant.common;

import com.ccicnet.gd.common.constant.BaseEnum;
import com.ccicnet.gd.customer.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tables implements BaseEnum {
    REGISTRY(0, CustomerRegistry.class.getSimpleName()),
    BASIC(1, CustomerBasic.class.getSimpleName()),
    ATTACH(2, CustomerAttach.class.getSimpleName()),
    ATTACH_IMAGE(3, CustomerAttachImage.class.getSimpleName()),
    BANKCARD(4, CustomerBankCard.class.getSimpleName()),
    CAREER(5, CustomerCareer.class.getSimpleName()),
    CONTACT(6, CustomerContact.class.getSimpleName()),
    HOUSE(7, CustomerHouse.class.getSimpleName()),
    HOUSE_OWNER(8, CustomerHouseOwner.class.getSimpleName()),
    LIFE_INSURANCE(9, CustomerLifeInsurance.class.getSimpleName()),
    VEHICLE(10, CustomerVehicle.class.getSimpleName()),
    RESIDENCE(11, CustomerResidence.class.getSimpleName()),
    ;

    private final Integer code;
    private final String description;
}
