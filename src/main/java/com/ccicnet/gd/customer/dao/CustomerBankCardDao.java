package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerBankCard;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerBankCardDao extends BaseDtDao<CustomerBankCard, Long> {
    List<CustomerBankCard> findByCustomerId(Long cutomerId);
}
