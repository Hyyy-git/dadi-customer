package com.ccicnet.gd.customer.dto.base;

import com.ccicnet.gd.common.dto.Dto;

public abstract class CustomerDto<T extends CustomerDto<T>> extends Dto {
    public abstract boolean hasValidKey();

    public abstract void copyKeyTo(T dto);
}
