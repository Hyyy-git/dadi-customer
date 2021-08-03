package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerContactApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactApplyDao extends BaseDtDao<CustomerContactApply, Long> {
    List<CustomerContactApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
