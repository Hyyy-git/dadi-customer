package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.entity.CustomerRegistry;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRegistryDao extends BaseDtDao<CustomerRegistry, Long> {
    Optional<CustomerRegistry> findByCertId(String certId);

    default CustomerRegistry getRegistry(Long customerId) {
        Optional<CustomerRegistry> optional = findById(customerId);
        if (!optional.isPresent()) {
            throw new BusinessException(BizError.CUSTOMER_NOT_FOUND, customerId);
        }

        return optional.get();
    }
}
