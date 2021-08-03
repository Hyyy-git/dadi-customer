package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.customer.entity.CustomerAttachImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAttachImageDao extends HasParentDao<CustomerAttachImage> {
    List<CustomerAttachImage> findByParentId(Long parentId);
}
