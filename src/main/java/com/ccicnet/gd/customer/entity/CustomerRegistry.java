package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户主表
 */
@Entity
@Getter
@Setter
@Table(name = "cust_registry")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerRegistry extends Dto implements BaseEntity<Long, Date> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerRegistry", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerRegistry", sequenceName = "CUST_REG_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String certId; //证件号码

    @CreatedDate
    private Date createTime;

    @JsonIgnore
    @Transient //注册表暂时不需要修改时间字段
    @LastModifiedDate
    private Date updateTime;
}
