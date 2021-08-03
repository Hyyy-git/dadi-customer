package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerCareerApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCareerApplyDao extends BaseDtDao<CustomerCareerApply, Long> {
    List<CustomerCareerApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
