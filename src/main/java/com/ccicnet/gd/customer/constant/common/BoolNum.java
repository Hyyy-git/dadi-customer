package com.ccicnet.gd.customer.constant.common;

import com.ccicnet.gd.common.constant.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoolNum implements BaseEnum {
    NO("0", "否"),
    YES("1", "是"),;

    private final String code;
    private final String description;
}
