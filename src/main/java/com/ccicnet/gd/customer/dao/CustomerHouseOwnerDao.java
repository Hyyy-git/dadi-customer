package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.customer.entity.CustomerHouseOwner;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerHouseOwnerDao extends HasParentDao<CustomerHouseOwner> {
    List<CustomerHouseOwner> findByParentId(Long parentId);
}