package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerResidence;
import com.ccicnet.gd.customer.entity.CustomerVehicle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerResidenceDao extends BaseDtDao<CustomerResidence, Long> {
    List<CustomerResidence> findByCustomerId(Long cutomerId);
}
