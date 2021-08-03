package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerCareer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCareerDao extends BaseDtDao<CustomerCareer, Long> {
    List<CustomerCareer> findByCustomerId(Long cutomerId);
}
