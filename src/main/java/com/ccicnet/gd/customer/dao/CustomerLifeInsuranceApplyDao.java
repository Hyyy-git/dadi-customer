package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerLifeInsuranceApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerLifeInsuranceApplyDao extends BaseDtDao<CustomerLifeInsuranceApply, Long> {
    List<CustomerLifeInsuranceApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
