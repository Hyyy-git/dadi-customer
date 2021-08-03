package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerContact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactDao extends BaseDtDao<CustomerContact, Long> {
    List<CustomerContact> findByCustomerId(Long cutomerId);
}
