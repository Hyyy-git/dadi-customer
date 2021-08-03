package com.ccicnet.gd.customer.dto.modal;

import com.ccicnet.gd.customer.dto.base.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户附件
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerAttachDto extends CustomerDto<CustomerAttachDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId;
    private String type; //影像类型

    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Transient
    private List<CustomerAttachImageDto> images = new ArrayList<>();

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(type);
    }

    @Override
    public void copyKeyTo(CustomerAttachDto dto) {
        dto.type = this.type;
    }
}
