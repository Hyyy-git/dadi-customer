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
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 客户居住信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerResidenceDto extends CustomerDto<CustomerResidenceDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId;
    private String province; //住宅地址（省）	--llas_biz_residence.ADDRESS_PROVINCE
    private String city; //住宅地址（市）	--llas_biz_residence.ADDRESS_CITY
    private String district; //住宅地址（区/县）	--llas_biz_residence.ADDRESS_AREA
    private String address; //住宅地址（详细地址）	--llas_biz_residence.ADDRESS_INFO
    @Size(min = 7)
    private String telephone; //住宅电话号码	--llas_biz_residence.AREA_CODE + "-" + FIXED_TELEPHONE
    private Integer recStatus; //是否有效 1:有效 2:无效	--llas_biz_residence.REC_STATUS

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(district) && StringUtils.isNotBlank(address);
    }

    @Override
    public void copyKeyTo(CustomerResidenceDto dto) {
        dto.district = this.district;
        dto.address = this.address;
    }
}
