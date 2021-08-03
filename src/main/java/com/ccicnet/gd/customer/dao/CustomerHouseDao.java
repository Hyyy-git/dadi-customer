package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerHouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerHouseDao extends BaseDtDao<CustomerHouse, Long> {
    List<CustomerHouse> findByCustomerId(Long cutomerId);
}
