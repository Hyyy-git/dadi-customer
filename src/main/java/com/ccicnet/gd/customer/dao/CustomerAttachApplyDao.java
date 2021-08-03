package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerAttach;
import com.ccicnet.gd.customer.entity.CustomerAttachApply;
import com.ccicnet.gd.customer.entity.CustomerCareerApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAttachApplyDao extends BaseDtDao<CustomerAttachApply, Long> {
    List<CustomerAttachApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
