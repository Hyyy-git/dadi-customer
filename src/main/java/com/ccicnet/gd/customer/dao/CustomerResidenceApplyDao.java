package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerResidenceApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerResidenceApplyDao extends BaseDtDao<CustomerResidenceApply, Long> {
    List<CustomerResidenceApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
