package com.ccicnet.gd.customer.constant.config;

import com.ccicnet.gd.common.constant.BaseEnum;
import lombok.Getter;

@Getter
public enum ConfigCatalogEnum implements BaseEnum {
    EXCEPTION_ALARM("EXCEPTION_ALARM", "异常告警"),

    //preserve line 建议在每一行枚举末尾都加上逗号，可以减少代码冲突
    ;

    private final String code; //对应 business_config_catalog.code
    private final String description; //对应 business_config_catalog.description

    /**
     * 找不到配置的时候，这项配置的默认值。值的类型必须和期望的配置类型一致。列表类型暂不支持默认值
     */
    private final Object defaultValue;

    ConfigCatalogEnum(String code, String description) {
        this.code = code;
        this.description = description;
        this.defaultValue = null;
    }

    ConfigCatalogEnum(String code, String description, Object defaultValue) {
        this.code = code;
        this.description = description;
        this.defaultValue = defaultValue;
    }
}
