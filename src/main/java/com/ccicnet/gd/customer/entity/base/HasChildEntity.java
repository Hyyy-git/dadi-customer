package com.ccicnet.gd.customer.entity.base;

import com.ccicnet.gd.common.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * @param <D> 子 dto 类型
 */
public interface HasChildEntity<D> extends BaseEntity<Long, Date> {//

    /**
     * 返回子 dto 列表
     */
    List<D> getChildren();
}
