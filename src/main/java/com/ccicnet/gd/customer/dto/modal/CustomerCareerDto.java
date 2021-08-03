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
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户职业信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerCareerDto extends CustomerDto<CustomerCareerDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId; //客户ID
    private Integer relation; //1本人，2配偶
    private String company; // 工作单位 	--llas_biz_business_apply.SELF/MATE_WORK_CORP,llas_biz_work_address.COMPANY_NAME
    private String department; // 所在部门 	--llas_biz_business_apply.SELF/MATE_DEPARTMENT
    private Integer isPrivateOwner; // 是否私营业主 	--llas_biz_business_apply.SELF/MATE_IS_PRIVATE_OWNER
    private String proportion; // 注册号 	--llas_biz_business_apply.SELF/MATE_PROPORTION
    private String province; // 单位地址（省） 	--llas_biz_business_apply.SELF/MATE_WORK_PROVINCE,llas_biz_work_address.ADDRESS_PROVINCE
    private String city; // 单位地址（市） 	--llas_biz_business_apply.SELF/MATE_WORK_CITY,llas_biz_work_address.ADDRESS_CITY
    private String district; // 单位地址（县/区） 	--llas_biz_business_apply.SELF/MATE_WORK_DISTRICT,llas_biz_work_address.ADDRESS_AREA
    private String address; // 单位地址（详细地址） 	--llas_biz_business_apply.SELF/MATE_WORK_PLACE,llas_biz_work_address.ADDRESS_INFO
    private String workCode; // 单位电话（区号） 	--llas_biz_business_apply.SELF/MATE_WORK_CODE,llas_biz_work_address.AREA_CODE
    private String workTel; // 单位电话 	--llas_biz_business_apply.SELF/MATE_WORK_TEL,llas_biz_work_address.FIXED_TELEPHONE
    private String workTelZone; // 单位电话(分机) 	--llas_biz_business_apply.SELF/MATE_WORK_TEL_ZONE
    private String workCode2; // 单位电话2（区号） 	--llas_biz_business_apply.SELF/MATE_WORK_CODE2
    private String workTel2; // 单位电话2 	--llas_biz_business_apply.SELF/MATE_WORK_TEL2
    private String workTelZone2; // 单位电话2(分机) 	--llas_biz_business_apply.SELF/MATE_WORK_TEL_ZONE2
    private String workZip; // 单位邮编 	--llas_biz_business_apply.SELF/MATE_WORK_ZIP
    private String paymentType; // 发薪方式: 网银、现金、网银+现金 	--llas_biz_business_apply.SELF/MATE_PAYMENT_TYPE
    private Integer salary; // 每月基本薪金 	--llas_biz_business_apply.SELF/MATE_SALARY
    private Date startDate; // 起始服务时间 	--llas_biz_business_apply.SELF/MATE_START_DATE

    @Max(31)
    @Min(1)
    private Integer paymentDay; // 支薪日 	--llas_biz_business_apply.SELF/MATE_PAYMENT_DAY
    private Integer otherEarn; // 其他收入 	--llas_biz_business_apply.SELF/MATE_OTHER_EARN
    private String corpType; // 企业类型 	--llas_biz_business_apply.SELF/MATE_CORP_TYPE
    private String industryCode; // 行业代码 	--llas_biz_business_apply.SELF/MATE_INDUSTRY_CODE
    private String rankLevel; // 职级代码 	--llas_biz_business_apply.SELF/MATE_RANK_LEVEL
    private Long registeredCapital; // 注册资本 	--llas_biz_business_apply.SELF/MATE_REGISTERED_CAPITAL
    private String corpDomainName; // 行业划分名称 	--llas_biz_business_apply.SELF/MATE_CORP_DOMAIN_NAME
    private String corpDomain; // 行业划分ID 	--llas_biz_business_apply.SELF/MATE_CORP_DOMAIN
    private String unitKind; // 单位性质 	--llas_biz_business_apply.SELF/MATE_UNIT_KIND
    private String occupation; // 职位级别ID 	--llas_biz_business_apply.SELF/MATE_OCCUPATION
    private String occupationName; // 职位级别名称 	--llas_biz_business_apply.SELF/MATE_OCCUPATION_NAME
    private String privateOwnerType; // 私营业主类型 	--llas_biz_business_apply.SELF/MATE_PRIVATE_OWNER_TYPE
    private Date setupDate; // 成立时间 	--llas_biz_business_apply.SELF/MATE_SETUP_DATE
    @Min(1)
    private BigDecimal stockPercent; // 股权占比 	--llas_biz_business_apply.SELF/MATE_STOCK_PERCENT
    private Integer operatePlace; // 经营场所 	--llas_biz_business_apply.SELF/MATE_OPERATE_PLACE
    @Min(1)
    private Integer employeeNum; // 员工人数 	--llas_biz_business_apply.SELF/MATE_EMPLOYEE_NUM
    private Integer isSocialeCurity; // 是否有社保 	--llas_biz_business_apply.SELF/MATE_IS_SOCIALE_CURITY
    private Long paymentBase; // 缴费基数 	--llas_biz_business_apply.SELF/MATE_PAYMENT_BASE
    private Date firstInsureDate; // 初次参保时间 	--llas_biz_business_apply.SELF/MATE_FIRST_INSURE_DATE
    private Integer insuredStatus; // 参保状态 	--llas_biz_business_apply.SELF/MATE_INSURED_STATUS
    private Integer isSameOrg; // 社保缴交单位与工作单位是否一致 	--llas_biz_business_apply.SELF/MATE_IS_SAME_ORG
    private String paymentWork; // 社保缴交单位 	--llas_biz_business_apply.SELF/MATE_PAYMENT_WORK
    private Integer isHouseReserveFund; // 是否有公积金 	--llas_biz_business_apply.SELF/MATE_IS_HOUSE_RESERVE_FUND
    private Double paymentBase2; // 缴存基数 	--llas_biz_business_apply.SELF/MATE_PAYMENT_BASE2
    private Double monthAmount; // 月缴存额 	--llas_biz_business_apply.SELF/MATE_MONTH_AMOUNT
    private Double perSaveRatio; // 个人缴存比例 	--llas_biz_business_apply.SELF/MATE_PER_SAVE_RATIO
    private Double companySaveRatio; // 公司缴存比例 	--llas_biz_business_apply.SELF/MATE_COMPANY_SAVE_RATIO
    private String payYearMonth; // 起缴年月 	--llas_biz_business_apply.SELF/MATE_PAY_YEAR_MONTH
    private Integer depositStatus; // 缴存状态 	--llas_biz_business_apply.SELF/MATE_DEPOSIT_STATUS
    private Integer isSameOrg2; // 公积金缴交单位与工作单位是否一致 	--llas_biz_business_apply.SELF/MATE_IS_SAME_ORG2
    private String paymentWork2; // 公积金缴交单位 	--llas_biz_business_apply.SELF/MATE_PAYMENT_WORK2
    private Long proofincome; // 收入证明 	--llas_biz_business_apply.SELF/MATE_PROOFINCOME
    private Integer orderNum; //序号

    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return relation != null && StringUtils.isNotBlank(company);
    }

    @Override
    public void copyKeyTo(CustomerCareerDto dto) {
        dto.relation = this.relation;
        dto.company = this.company;
    }
}
