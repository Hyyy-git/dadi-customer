package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerVehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerVehicleDao extends BaseDtDao<CustomerVehicle, Long> {
    List<CustomerVehicle> findByCustomerId(Long cutomerId);
}
