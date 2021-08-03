package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAccountDao extends BaseDtDao<CustomerAccount, Long> {
    Optional<CustomerAccount> findByPhoneNo(String phone);

    default CustomerAccount getOneByPhoneNo(String phone) {
        Optional<CustomerAccount> optional = findByPhoneNo(phone);
        if (!optional.isPresent()) {
            throw new BusinessException(BizError.ACCOUNT_NOT_FOUND);
        }
        return optional.get();
    }

    default CustomerAccount getOneById(Long id) {
        Optional<CustomerAccount> optional = findById(id);
        if (!optional.isPresent()) {
            throw new BusinessException(BizError.ACCOUNT_NOT_FOUND);
        }
        return optional.get();
    }
}
