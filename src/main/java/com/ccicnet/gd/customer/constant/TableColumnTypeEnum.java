package com.ccicnet.gd.customer.constant;

import com.ccicnet.gd.customer.entity.base.TableColumnEntity;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Acmen
 * @ClassName TableColumnTypeEnum.java
 * @Description TODO
 * @CreateTime 2020年11月03日 15:35:13
 */
@Getter
public enum TableColumnTypeEnum {
    NVARCHAR2,
    VARCHAR2,
    NUMBER,
    DATE;


    public  boolean match(String type){
        if (StringUtils.isEmpty(type)){
            return Boolean.FALSE;
        }
        return type.equals(this.toString());
    }
}
