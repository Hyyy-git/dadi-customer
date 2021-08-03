package com.ccicnet.gd.customer.dto.config;

import com.ccicnet.gd.customer.entity.BusinessConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 查询配置表的过滤条件，哪个字段不为空，则在查询 {@link BusinessConfig} 配置表的 where 条件中会增加哪个字段
 */
@Getter
@Setter
@ToString
public class ConfigCondition {
    private String productCode; //对应 BusinessConfig.productCode 字段
    private String orgId;       //对应 BusinessConfig.orgId       字段
    private String fundCode;    //对应 BusinessConfig.fundCode    字段
    private String configKey;   //对应 BusinessConfig.configKey   字段
    private Date sysDate;       //对应 当前系统时间

    public ConfigCondition productCode(String productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public ConfigCondition orgId(String orgId) {
        this.setOrgId(orgId);
        return this;
    }

    public ConfigCondition fundCode(String fundCode) {
        this.setFundCode(fundCode);
        return this;
    }

    public ConfigCondition configKey(String configKey) {
        this.setConfigKey(configKey);
        return this;
    }

    public ConfigCondition sysDate(Date sysDate) {
        this.setSysDate(sysDate);
        return this;
    }
}
