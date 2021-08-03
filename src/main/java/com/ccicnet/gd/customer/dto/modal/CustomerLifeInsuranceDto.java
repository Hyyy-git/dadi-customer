package com.ccicnet.gd.customer.dto.modal;

import com.ccicnet.gd.customer.dto.base.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * 客户寿险信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerLifeInsuranceDto extends CustomerDto<CustomerLifeInsuranceDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId;
    private String payWay; // 缴费方式 	--LLAS_BIZ_CUST_LIFEINSURANCE.PAY_WAY
    private String payCategory; // 缴别 	--LLAS_BIZ_CUST_LIFEINSURANCE.PAY_CATEGORY
    private Integer needPayYears; // 需缴费年数 	--LLAS_BIZ_CUST_LIFEINSURANCE.NEED_PAY_YEARS
    private Integer policyAllPaid; // 保单总保额 	--LLAS_BIZ_CUST_LIFEINSURANCE.POLICY_ALL_PAID
    private Integer premiumPaid; // 期缴保费 	--LLAS_BIZ_CUST_LIFEINSURANCE.PREMIUM_PAID
    private Integer premiumPaidMonth; // 期缴保费月保费 	--LLAS_BIZ_CUST_LIFEINSURANCE.PREMIUM_PAID_MONTH
    private Date validDate; // *保单生效日期 	--LLAS_BIZ_CUST_LIFEINSURANCE.POLICY_OP_DATE
    private String type; // *险种类型 	--LLAS_BIZ_CUST_LIFEINSURANCE.INSURANCE_TYPE
    private String recognizeeName; // 被保人姓名 	--LLAS_BIZ_CUST_LIFEINSURANCE.RECOGNIZEENAME
    private String policyHolder; // 投保人姓名 	--LLAS_BIZ_CUST_LIFEINSURANCE.POLICYHOLDER
    private String phonenumber1; // 被保险人电话 	--LLAS_BIZ_CUST_LIFEINSURANCE.PHONENUMBER1
    private String phonenumber2; // 投保人电话 	--LLAS_BIZ_CUST_LIFEINSURANCE.PHONENUMBER2
    private String insuranceCompany; // *保险公司 	--LLAS_BIZ_CUST_LIFEINSURANCE.INSURANCECOMPANY
    private String province; // 投保地点（省） 	--LLAS_BIZ_CUST_LIFEINSURANCE.PROVINCE
    private String city; // 投保地点（市） 	--LLAS_BIZ_CUST_LIFEINSURANCE.CITY
    private String memo; // 备注信息1 	--LLAS_BIZ_CUST_LIFEINSURANCE.REMARK1 + MEMO1
    private String policyName; //保单名称	--LLAS_BIZ_CUST_LIFEINSURANCE.POLICY_NAME
    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效	--LLAS_BIZ_CUST_LIFEINSURANCE.REC_STATUS

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return validDate != null && StringUtils.isNotBlank(type) && StringUtils.isNotBlank(insuranceCompany);
    }

    @Override
    public void copyKeyTo(CustomerLifeInsuranceDto dto) {
        dto.validDate = this.validDate;
        dto.type = this.type;
        dto.insuranceCompany = this.insuranceCompany;
    }
}
