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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 客户联系信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerContactDto extends CustomerDto<CustomerContactDto> {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Long customerId;
    private Integer relation; // 关联关系 	--LLAS_BIZ_RELATIVE.RELATION
    private String name; // 姓名 	--LLAS_BIZ_RELATIVE.FULL_NAME
    private String phoneNo; // 手机号 	--LLAS_BIZ_RELATIVE.MOBILE, LLAS_BIZ_RELATIVE.TELEPHONE, customer_tel.TELEPHONE
    private String remark; //备注	--LLAS_BIZ_RELATIVE.REMARK + CHECK_MEMO
    private Integer orderNum; //排序	--LLAS_BIZ_RELATIVE.ORDER_NUM

    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效	--LLAS_BIZ_RELATIVE.REC_STATUS

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(name);
    }

    @Override
    public void copyKeyTo(CustomerContactDto dto) {
        dto.name = this.name;
    }
}
