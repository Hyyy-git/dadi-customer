package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerBankCardDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
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
@Table(name = "cust_bank_card_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerBankCardApply extends CustomerBankCardDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerBankCardApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerBankCardApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}