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
 * 客户车辆信息
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerVehicleDto extends CustomerDto<CustomerVehicleDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId;
    private String vehicleLicense; //车牌号 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_LICENSE
    private String carType; // 车辆类型 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_TYPE
    private String vehicleModel; // 车辆型号 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_MODEL
    private Long purchaseSum; // 购置金额 	--LLAS_BIZ_CUSTOMER_VEHICLE.PURCHASE_SUM
    private String vehicleBrand; // 车辆品牌 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_BRAND
    private String carOwner; // 车主姓名 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_OWNER
    private String carInsurance; // 车险类型 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_INSURANCE
    private String carInsuranceCompany; // 保险公司 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_INSURANCE_COMPANY
    private Date insuranceBegintime; // 保险起期 	--LLAS_BIZ_CUSTOMER_VEHICLE.INSURANCE_BEGINTIME
    private Date insuranceEndtime; // 保险止期 	--LLAS_BIZ_CUSTOMER_VEHICLE.INSURANCE_ENDTIME
    private Integer carInsurancePremium; // 车险保费 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_INSURANCE_PREMIUM
    private String insuerName; // 保险人姓名 	--LLAS_BIZ_CUSTOMER_VEHICLE.INSUER_NAME
    private String contactWay; // 联系方式 	--LLAS_BIZ_CUSTOMER_VEHICLE.CONTACT_WAY
    private String contactAddress; // 联系地址 	--LLAS_BIZ_CUSTOMER_VEHICLE.CONTACT_ADDRESS
    private String vehicledIsPlacement; // 排量 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLED_IS_PLACEMENT
    private Date transferTime; // 过户时间 	--LLAS_BIZ_CUSTOMER_VEHICLE.TRANSFER_TIME
    private Integer vehicleRange; // 行驶里程 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_RANGE
    private Date purchaseDate; // 购置日期 	--LLAS_BIZ_CUSTOMER_VEHICLE.PURCHASEDATE
    private String isUsedCar; // 是否二手车 	--LLAS_BIZ_CUSTOMER_VEHICLE.IS_USED_CAR
    private Integer transferTurnTime; // 转手次数	--LLAS_BIZ_CUSTOMER_VEHICLE.TRANSFER_TURN_TIME
    private String vehicleSituation; // 车辆状况 	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_SITUATION
    private String carStartDate; // 车贷起始日期 	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_STARTDATE
    private String underSpanId; // 车架号 	--LLAS_BIZ_CUSTOMER_VEHICLE.UNDER_SPAN_ID
    private String engineId; // 发动机号 	--LLAS_BIZ_CUSTOMER_VEHICLE.ENGINE_ID
    private String memo; // 备注信息 	--LLAS_BIZ_CUSTOMER_VEHICLE.MEMO+MEMO1
    private Date firstInsuranceDate; //首次投保日期	--LLAS_BIZ_CUSTOMER_VEHICLE.FIRST_INSURANCE_DATE
    private String isRenewal; //是否续保客户	--LLAS_BIZ_CUSTOMER_VEHICLE.IS_RENEWAL
    private Integer conRenewalYears; //连续续保年限	--LLAS_BIZ_CUSTOMER_VEHICLE.CON_RENEWAL_YEARS
    private Integer curSaliPremium; //交强险保费	--LLAS_BIZ_CUSTOMER_VEHICLE.CUR_SALI_PREMIUM
    private Integer curBusiPremium; //商业三者险保费	--LLAS_BIZ_CUSTOMER_VEHICLE.CUR_BUSI_PREMIUM
    private Integer curLossPremium; //车损险保费	--LLAS_BIZ_CUSTOMER_VEHICLE.CUR_LOSS_PREMIUM
    private Integer curElsePremium; //附加险保费	--LLAS_BIZ_CUSTOMER_VEHICLE.CUR_ELSE_PREMIUM
    private String purchaseStatus; // 付款情况	--LLAS_BIZ_CUSTOMER_VEHICLE.PURCHASE_STATUS
    private Date salesDate; // 卖出日期	--LLAS_BIZ_CUSTOMER_VEHICLE.SALES_DATE
    private String contractNo; // 合同编号	--LLAS_BIZ_CUSTOMER_VEHICLE.CONTRACT_NO
    private Integer vehicleYear; // 车辆年限	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_YEAR
    private String vehicleName; // 车辆名称	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_NAME
    private String vehiclePurpose; // 车辆用途	--LLAS_BIZ_CUSTOMER_VEHICLE.VEHICLE_PURPOSE
    private Integer mortgageRatio; // 按揭比例 	--LLAS_BIZ_CUSTOMER_VEHICLE.MORTGAGE_RATIO
    private String mortgageType; // 按揭类型 	--LLAS_BIZ_CUSTOMER_VEHICLE.MORTGAGE_TYPE
    private BigDecimal repaymentRate; //还款利率	--LLAS_BIZ_CUSTOMER_VEHICLE.REPAYMENT_RATE
    private Integer repayment; //还款金额	--LLAS_BIZ_CUSTOMER_VEHICLE.REPAYMENT
    private Integer carMonthMoney; //月供	--LLAS_BIZ_CUSTOMER_VEHICLE.CARMONTHMONEY
    private Date dateOfIssuance; //发证日期	--LLAS_BIZ_CUSTOMER_VEHICLE.DATE_OF_ISSUANCE
    private Integer isCarCustome; //是否大地车客户，1：是，2：否	--LLAS_BIZ_CUSTOMER_VEHICLE.IS_CAR_CUSTOME
    private Integer refPurchaseSum; //精友参考购置价	--LLAS_BIZ_CUSTOMER_VEHICLE.REF_PURCHASE_SUM
    private String ocrContactAddress; //ocr联系地址	--LLAS_BIZ_CUSTOMER_VEHICLE.OCR_CONTACT_ADDRESS
    private String useType; //ocr使用性质	--LLAS_BIZ_CUSTOMER_VEHICLE.USE_TYPE
    private String carOcrOwner; //ocr所有人	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_OCR_OWNER
    private String ocrCarType; //ocr车辆类型	--LLAS_BIZ_CUSTOMER_VEHICLE.OCR_CAR_TYPE
    private Integer orderNum; //序号	--LLAS_BIZ_CUSTOMER_VEHICLE.CAR_FLAG_ID
    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效	--LLAS_BIZ_CUSTOMER_VEHICLE.REC_STATUS

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(vehicleLicense);
    }

    @Override
    public void copyKeyTo(CustomerVehicleDto dto) {
        dto.vehicleLicense = this.vehicleLicense;
    }
}
