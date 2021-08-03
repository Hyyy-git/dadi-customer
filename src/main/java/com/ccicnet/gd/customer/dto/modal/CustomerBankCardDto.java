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
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 客户银行卡
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomerBankCardDto extends CustomerDto<CustomerBankCardDto> {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long customerId; //客户号

    private String bankName; //银行名称
    private String bankCode; //银行代码

    private String cardNo; //卡号

    @Max(2)
    @Min(0)
    private Integer accountLevel; //0，未知；1，一类户；2，二类户

    @Size(min = 11, max = 11)
    private String phoneNo; //绑定手机号

    @Max(2)
    @Min(1)
    private Integer recStatus; //是否有效 1:有效 2:无效

    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date updateTime;

    @Override
    public boolean hasValidKey() {
        return StringUtils.isNotBlank(cardNo);
    }

    @Override
    public void copyKeyTo(CustomerBankCardDto dto) {
        dto.cardNo = this.cardNo;
    }
}
