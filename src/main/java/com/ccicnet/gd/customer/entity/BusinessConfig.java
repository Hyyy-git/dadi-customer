package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.constant.config.ConfigCatalogEnum;
import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.common.environment.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table(name = "cust_business_config")
public class BusinessConfig extends Dto implements BaseEntity<Long, Date> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "BusinessConfig", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "BusinessConfig", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    /**
     * {@link ConfigCatalogEnum}
     */
    private String catalogCode; //配置大类，见 business_config_catalog 表

    private String configKey; //键
    private String configValue; //值
    private String remark; //备注

    private Long parentId; //父主键，用于表达层级关系
    private Long levelNum; //当前层级，用于表达层级关系
    private Long sortId; //显示顺序

    private Date validTime; //生效时间，数据必须满足 “生效时间 <= 系统时间 < 失效时间” 才算有效数据
    private Date expiryTime; //失效时间

    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;

    /**
     * 尽量不要直接在声明 entity 成员属性的时候赋默认值，以免使用 Example.of 查找数据库时找不到对应数据。
     * 推荐应该使用本方法。
     * 需要初始化默认值的时候，手动调用本方法。
     */
    public void init() {
        parentId = 0L;
        levelNum = 0L;
        sortId = 0L;
        validTime = DateUtil.fromString("2000-01-01");
        expiryTime = DateUtil.fromString("3000-01-01");
        createUser = "system";
        updateUser = "system";
    }
}
