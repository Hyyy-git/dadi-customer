package com.ccicnet.gd.customer.entity.base;

import com.ccicnet.gd.common.entity.BaseEntity;

import java.util.Date;

public interface CustomerEntity extends BaseEntity<Long, Date> {
    Long getCustomerId();

    void setCustomerId(Long customerId);
}
