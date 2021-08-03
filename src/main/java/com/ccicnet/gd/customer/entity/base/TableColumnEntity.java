package com.ccicnet.gd.customer.entity.base;

import lombok.Data;

/**
 * @author Acmen
 * @ClassName TableColumnsEntity.java
 * @Description 数据库表列相关信息
 * @CreateTime 2020年11月03日 11:01:54
 */
@Data
public class TableColumnEntity {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 数据类型长度（非number类型的定义长度，number类型的最大长度）
     */
    private Integer dataLength;
    /**
     * number类型的小数点前的数据长度
     */
    private Integer dataPrecision;
    /**
     * number类型的小数位数长度
     */
    private Integer dataScale;
}
