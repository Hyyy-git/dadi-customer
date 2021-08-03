package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerResidenceDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
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
@Table(name = "cust_residence_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerResidenceApply extends CustomerResidenceDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerResidenceApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerResidenceApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}
