package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.customer.entity.CustomerAttachImageApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAttachImageApplyDao extends HasParentDao<CustomerAttachImageApply> {
    List<CustomerAttachImageApply> findByParentId(Long parentId);
}
