package com.ccicnet.gd.customer.dto.config;

import com.ccicnet.gd.customer.constant.config.ConfigCatalogEnum;
import com.ccicnet.gd.common.dto.PageableReq;
import com.ccicnet.gd.common.dto.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 查询多条配置 请求报文
 */
@Getter
@Setter
public class QueryConfigsReq extends Request {
    private static final long serialVersionUID = 1L;
    private final Integer version = 1;

    private String fundCode; //资金方编码
    private String productCode; //产品编码
    private String orgId; //机构编码
    private String configKey; //配置键
    private String configValue; //配置值（模糊查询）

    private ConfigCatalogEnum catalog; //配置大类

    private Date updateTimeBegin; //更新日期 开始
    private Date updateTimeEnd; //更新日期 结束

    private PageableReq page;        //分页信息
}
