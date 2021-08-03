package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerHouseDto;
import com.ccicnet.gd.customer.dto.modal.CustomerHouseOwnerDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 客户房产信息表
 */
@Entity
@Getter
@Setter
@Table(name = "cust_house_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerHouseApply extends CustomerHouseDto implements ApplyEntity, HasChildEntity<CustomerHouseOwnerDto> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerHouseApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerHouseApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号

    @JsonIgnore
    @Override
    public List<CustomerHouseOwnerDto> getChildren() {
        return getOwners();
    }
}
