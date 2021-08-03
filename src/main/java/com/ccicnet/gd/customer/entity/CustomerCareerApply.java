package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerCareerDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户职业信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_career_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerCareerApply extends CustomerCareerDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerCareerApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerCareerApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}
