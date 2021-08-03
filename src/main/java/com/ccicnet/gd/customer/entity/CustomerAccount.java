package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 账户主表
 */
@Entity
@Getter
@Setter
@Table(name = "cust_account")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerAccount extends Dto implements BaseEntity<Long, Date> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerAccount", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerAccount", sequenceName = "CUST_ACC_SEQ", allocationSize = 1)
    @Id
    private Long id;

    /**
     * 手机号码
     */
    private String phoneNo;

    /**
     * 推广渠道
     */
    private String channel;

    /**
     * 是否销户状态
     */
    private Boolean closed;

    /**
     * 实名客户ID
     */
    private Long customerId;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
