package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerLifeInsuranceDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户寿险信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_life_insurance_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerLifeInsuranceApply extends CustomerLifeInsuranceDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerLifeInsuranceApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerLifeInsuranceApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}
