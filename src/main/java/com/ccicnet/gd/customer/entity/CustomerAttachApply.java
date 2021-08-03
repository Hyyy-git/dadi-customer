package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerAttachDto;
import com.ccicnet.gd.customer.dto.modal.CustomerAttachImageDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 客户附件
 */
@Entity
@Getter
@Setter
@Table(name = "cust_attach_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerAttachApply extends CustomerAttachDto implements ApplyEntity, HasChildEntity<CustomerAttachImageDto> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerAttachApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerAttachApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id;
    private String applyNo; //申请号

    @JsonIgnore
    @Override
    public List<CustomerAttachImageDto> getChildren() {
        return getImages();
    }
}
