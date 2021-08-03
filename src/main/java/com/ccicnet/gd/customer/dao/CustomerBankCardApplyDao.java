package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerBankCardApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerBankCardApplyDao extends BaseDtDao<CustomerBankCardApply, Long> {
    List<CustomerBankCardApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
