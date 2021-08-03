package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.customer.entity.CustomerHouseOwnerApply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerHouseOwnerApplyDao extends HasParentDao<CustomerHouseOwnerApply> {
    List<CustomerHouseOwnerApply> findByParentId(Long parentId);
}