package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerVehicleApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerVehicleApplyDao extends BaseDtDao<CustomerVehicleApply, Long> {
    List<CustomerVehicleApply> findByCustomerIdAndApplyNo(Long cutomerId, String applyNo);
}
