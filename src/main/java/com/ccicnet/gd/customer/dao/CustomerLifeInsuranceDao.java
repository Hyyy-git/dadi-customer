package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerLifeInsurance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerLifeInsuranceDao extends BaseDtDao<CustomerLifeInsurance, Long> {
    List<CustomerLifeInsurance> findByCustomerId(Long cutomerId);
}
