package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerCareerDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
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
@Table(name = "cust_career")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerCareer extends CustomerCareerDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerCareer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerCareer", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
}
