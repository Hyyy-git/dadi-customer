package com.ccicnet.gd.customer.dto.modal;

import com.ccicnet.gd.customer.dto.base.CustomerDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * 客户基本信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerBasicDto extends CustomerDto<CustomerBasicDto> {
    private static final long serialVersionUID = 1L;

    private String name; //姓名
    private String ethnicity; //民族	--llas_biz_business_apply.ETHNICITY
    private Date startDate; //身份证有限期开始日
    private Date endDate; //身份证有效期结束日，如果是长期有效，则填写 3000-01-01
    private String issueOrg; //身份证发证机关

    private String phoneNo; //手机号码 --llas_biz_business_apply.MOBILE
    @Email
    private String email; //电子邮箱  --LLAS_BIZ_BUSINESS_APPLY.EMAIL_ADD
    private Integer marriage; //婚姻状态    --LLAS_BIZ_BUSINESS_APPLY.MARRIAGE

    private String eduDegree; //学历  --LLAS_BIZ_BUSINESS_APPLY.EDU_DEGREE
    private String eduDegreeCheck; //验证学历   --LLAS_BIZ_BUSINESS_APPLY.EDU_DEGREE_CHECK
    private String eduDegreeType; //学历类型，全日制/非全日制   --LLAS_BIZ_BUSINESS_APPLY.EDU_GRADUATE_TYPE
    private String eduGraduate; //毕业类型，毕业/结业    --LLAS_BIZ_BUSINESS_APPLY.EDU_GRADUATE_TYPE

    private String nativeProvince; //户籍省            --LLAS_BIZ_BUSINESS_APPLY.NATIVE_PROVINCE
    private String nativeCity; //户籍市                --LLAS_BIZ_BUSINESS_APPLY.NATIVE_CITY
    private String nativeDistrict; //户籍区            --LLAS_BIZ_BUSINESS_APPLY.NATIVE_DISTRICT
    private String nativeAddress; //户籍地址            --LLAS_BIZ_BUSINESS_APPLY.NATIVE_PLACE
    private String nativeZipcode; //户籍邮编            --LLAS_BIZ_BUSINESS_APPLY.NATIVE_ZIP

    private String familyProvince; //住宅省    --LLAS_BIZ_BUSINESS_APPLY.FAMILY_PROVINCE
    private String familyCity; //住宅市      --LLAS_BIZ_BUSINESS_APPLY.FAMILY_CITY
    private String familyDistrict; //住宅区	 --LLAS_BIZ_BUSINESS_APPLY.FAMILY_DISTRICT
    private String familyAddress; //住宅地址 --LLAS_BIZ_BUSINESS_APPLY.FAMILY_PLACE
    private String familyZipcode; //住宅邮编 --LLAS_BIZ_BUSINESS_APPLY.FAMILY_ZIP
    private String familyTel; // 住宅电话 	--LLAS_BIZ_BUSINESS_APPLY.FAMILY_TEL

    private Date liveBeginDate; //起始居住时间 --LLAS_BIZ_BUSINESS_APPLY.LIVING_BEGIN_DATE
    private Integer nativeRelation; //与户主关系         --LLAS_BIZ_BUSINESS_APPLY.NATIVE_RELATIVE
    private Integer isNative; // 是否本地户籍 	--LLAS_BIZ_BUSINESS_APPLY.IS_NATIVE
    private Integer fendPerson; //供养人数  --LLAS_BIZ_BUSINESS_APPLY.LIVING_PERSON
    private String houseCondition; //居住状况

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    /**
     * 没有关键字段，直接返回 true
     */
    @Override
    public boolean hasValidKey() {
        return true;
    }

    /**
     * 没有关键字段，无需复制
     */
    @Override
    public void copyKeyTo(CustomerBasicDto dto) {

    }
}
