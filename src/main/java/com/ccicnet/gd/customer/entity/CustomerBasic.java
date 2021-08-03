package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerBasicDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户基本信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_basic")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerBasic extends CustomerBasicDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id; //主键 = Customer.id

    @JsonIgnore
    @Override
    public Long getCustomerId() {
        return getId();
    }

    @JsonIgnore
    @Override
    public void setCustomerId(Long customerId) {
        setId(customerId);
    }
}
