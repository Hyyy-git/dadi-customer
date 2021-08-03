package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerBasicDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户基本信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_basic_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerBasicApply extends CustomerBasicDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerBasicApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerBasicApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id;
    private Long customerId;
    private String applyNo; //申请号
}
