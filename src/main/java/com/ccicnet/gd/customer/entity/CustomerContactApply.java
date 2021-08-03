package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerContactDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
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
@Table(name = "cust_contact_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerContactApply extends CustomerContactDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerContactApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerContactApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}
