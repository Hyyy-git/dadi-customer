package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerContactDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户联系信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_contact")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerContact  extends CustomerContactDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerContact", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerContact", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
}
