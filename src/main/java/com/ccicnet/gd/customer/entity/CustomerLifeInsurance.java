package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerLifeInsuranceDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
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
@Table(name = "cust_life_insurance")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerLifeInsurance extends CustomerLifeInsuranceDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerLifeInsurance", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerLifeInsurance", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
}
