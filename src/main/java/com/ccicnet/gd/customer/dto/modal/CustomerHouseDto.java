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
 * 客户房产信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerHouseDto extends CustomerDto<CustomerHouseDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId;
    private Integer isBackup; //1主要房产/2备用房产，llas_biz_business_apply 的抵押房产信息一起录入到这里，填1
    private Integer realtyOther; // 是否有商品房 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_OTHER
    private String realtyAttribute; // 房产类别 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ATTRIBUTE,llas_biz_business_apply.HOUSE_AREA_TYPE
    private Date housePurchasedate; // 买入日期 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.HOUSE_PURCHASEDATE
    private Long buildPrice; // 建购价格 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.BUILD_PRICE
    private Long realtyArea; // 建筑面积 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_AREA,llas_biz_business_apply.HOUSE_AREA
    private String province; // 房产地址（省） 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ADD_PROVINCE,llas_biz_business_apply.HOUSE_PROVINCE
    private String city; // 房产地址（市） 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ADD_CITY,llas_biz_business_apply.HOUSE_CITY
    private String district; // *房产地址（县） 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ADD_DISTRICT,llas_biz_business_apply.DISTRICT
    private String address; // *房产地址（详细地址） 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ADD,llas_biz_business_apply.ADDRESS
    private Integer isLocal; // 是否本地 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.IS_LOCAL,llas_biz_business_apply.REALTY_ISLOCAL
    private String realtyZip; // 房产邮编 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_ZIP
    private Integer ownerNum; // 房产共有人数 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.OWNERS
    private Integer guaranty; // 是否为房抵押 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.IS_FLAG
    private Integer mortgage; // 是否有抵押，0无抵押，1一押，2二押 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.MORTGAGE 或 llas_biz_business_apply.ONE_OR_TWO_MORTGAGE
    private Long shareProp; // 所占份额 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.SHARE_PROP
    private Date saleDate; // 卖出日期 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.SALE_DATE
    private String certificateNo; // 产权证号 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.CERTIFICATE_NO
    private String realtyName; // 房屋名称 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REALTY_NAME
    private String contractNo; // 合同编号	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.CONTRACT_NO,llas_biz_business_apply.MORTAGE_CONTRACT
    private String memo; // 备注信息 	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.MEMO+MEMO1
    private Long housingRepaymentRate; //还款利率	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.HOUSING_REPAYMENT_RATE
    private Long housingRepayment; //还款金额	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.HOUSING_REPAYMENT
    private Integer houseLoanYetTime; //房贷已还款时长	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.HOUSE_LOAN_YET_TIME
    private Integer houseLoanTime; //房贷还款时长	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.HOUSE_LOAN_TIME
    private Integer isFamilyAddress; //房产地址是否同住宅地址：1-是；2-否	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.IS_FAMILY_ADDRESS
    private Integer houseRent; //房屋租赁情况(1-是,2-否)	--LLAS_BIZ_CUST_BACKUP_REALTY.HOUSE_RENT_INFO

    private Integer useOfLand; // 土地用途登记 	--llas_biz_business_apply.USE_OF_LAND
    private Date holdingTime; // 房产持证时间 	--llas_biz_business_apply.HOLDING_TIME
    private Date completedTime; // 房产竣工时间 	--llas_biz_business_apply.COMPLETED_TIME
    private Long estimatedValue; // 房产预估值 	--llas_biz_business_apply.ESTIMATED_VALUE
    private Integer isOnlyHouse; // 是否唯一住房 	--llas_biz_business_apply.IS_ONLY_HOUSE
    private Integer realtyIsmortgage; // 是否为银行按揭 	--llas_biz_business_apply.REALTY_ISMORTGAGE
    private String firmNameone; // 公司一名称 	--llas_biz_business_apply.FIRM_NAMEONE
    private Integer firmValueone; // 公司一评估价格 	--llas_biz_business_apply.FIRM_VALUEONE
    private String firmNametwo; // 公司二名称 	--llas_biz_business_apply.FIRM_NAMETWO
    private Integer firmValuetwo; // 公司二评估价格 	--llas_biz_business_apply.FIRM_VALUETWO
    private String firmNamethree; // 公司三名称 	--llas_biz_business_apply.FIRM_NAMETHREE
    private String preFirmName; // 预评估选定公司 	--llas_biz_business_apply.PRE_FIRM_NAME
    private Integer firmValueThree; // 公司三评估价格 	--llas_biz_business_apply.FIRM_VALUE_THREE
    private Integer preFirmValue; // 预评估选定价格 	--llas_biz_business_apply.PRE_FIRM_VALUE
    private Long firstBalance; // 一押贷款余额 	--llas_biz_business_apply.FIRST_BALANCE
    private String firstDealBank; // 一押办理银行 	--llas_biz_business_apply.FIRST_DEAL_BANK

    private Integer orderNum; //序号
    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效	--LLAS_BIZ_CUSTOMER_REALTY/LLAS_BIZ_CUST_BACKUP_REALTY.REC_STATUS

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Transient
    private List<CustomerHouseOwnerDto> owners = new ArrayList<>();

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(district) && StringUtils.isNotBlank(address);
    }

    @Override
    public void copyKeyTo(CustomerHouseDto dto) {
        dto.district = this.district;
        dto.address = this.address;
    }
}
