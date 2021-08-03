package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerAttachApply;
import com.ccicnet.gd.customer.entity.CustomerHouse;
import com.ccicnet.gd.customer.entity.CustomerHouseApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerHouseApplyDao extends BaseDtDao<CustomerHouseApply, Long> {
    List<CustomerHouseApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
