package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerBankCardDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户银行卡
 */
@Entity
@Getter
@Setter
@Table(name = "cust_bank_card")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerBankCard extends CustomerBankCardDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerBankCard", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerBankCard", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
}
