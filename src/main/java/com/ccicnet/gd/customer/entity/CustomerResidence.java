package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerResidenceDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户居住信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_residence")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerResidence extends CustomerResidenceDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerResidence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerResidence", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
}
