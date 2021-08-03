package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerBasicApply;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerBasicApplyDao extends BaseDtDao<CustomerBasicApply, Long> {
    Optional<CustomerBasicApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
