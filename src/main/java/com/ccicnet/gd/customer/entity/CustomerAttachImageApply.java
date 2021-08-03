package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.customer.dto.modal.CustomerAttachImageDto;
import com.ccicnet.gd.customer.entity.base.HasParentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户附件的每张影像
 */
@Entity
@Getter
@Setter
@Table(name = "cust_attach_image_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerAttachImageApply extends CustomerAttachImageDto implements BaseEntity<Long, Date>, HasParentEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerAttachImageApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerAttachImageApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(name = "attach_id")
    private Long parentId;
}
