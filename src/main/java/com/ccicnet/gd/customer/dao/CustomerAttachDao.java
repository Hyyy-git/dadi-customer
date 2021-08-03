package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerAttach;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAttachDao extends BaseDtDao<CustomerAttach, Long> {
    List<CustomerAttach> findByCustomerId(Long cutomerId);
}
