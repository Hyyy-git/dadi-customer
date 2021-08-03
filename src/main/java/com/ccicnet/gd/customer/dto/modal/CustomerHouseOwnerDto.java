package com.ccicnet.gd.customer.dto.modal;

import com.ccicnet.gd.common.dto.Dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 客户房产产权人信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerHouseOwnerDto extends Dto {
    private static final long serialVersionUID = 1L;

    private String name; // 产权人姓名 	--llas_biz_business_apply.OWNER_NAME,LLAS_BIZ_HOUSE_PROPERTY_OWNER.OWNER_NAME
    private Integer relation; // 关系 	--las_biz_business_apply.RELATION_SHIP, LLAS_BIZ_HOUSE_PROPERTY_OWNER.RELATION_SHIP
    @Size(min = 15, max = 18)
    private String certId; // 产权人身份证号码 	--llas_biz_business_apply.ID_CARD, LLAS_BIZ_HOUSE_PROPERTY_OWNER.ID_CARD
    private String phoneNo; // 联系方式 	--llas_biz_business_apply.TEL, LLAS_BIZ_HOUSE_PROPERTY_OWNER.TEL
    private String memo; // 备注信息 	--LLAS_BIZ_HOUSE_PROPERTY_OWNER.MEMO
    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效	--LLAS_BIZ_HOUSE_PROPERTY_OWNER.REC_STATUS

    @JsonIgnore
    @Transient
    private Date createTime;

    @JsonIgnore
    @Transient
    private Date updateTime;
}
