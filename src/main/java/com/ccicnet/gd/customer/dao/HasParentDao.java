package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.customer.entity.base.HasParentEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface HasParentDao<CE extends BaseEntity<Long, Date> & HasParentEntity> extends BaseDtDao<CE, Long> {
    List<CE> findByParentId(Long parentId);
}
