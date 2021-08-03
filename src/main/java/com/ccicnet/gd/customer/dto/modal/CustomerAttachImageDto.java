package com.ccicnet.gd.customer.dto.modal;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.customer.entity.CustomerAttach;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户附件的每张影像
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerAttachImageDto extends Dto {
    private static final long serialVersionUID = 1L;

    private Long imageId; //影像平台的imageId
    private String businessNo; //影像平台的businessNo

    @JsonIgnore
    @Transient
    private Date createTime;

    @JsonIgnore
    @Transient
    private Date updateTime;
}
