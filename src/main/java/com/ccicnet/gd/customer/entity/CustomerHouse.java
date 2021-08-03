package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerHouseDto;
import com.ccicnet.gd.customer.dto.modal.CustomerHouseOwnerDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 客户房产信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_house")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerHouse extends CustomerHouseDto implements CustomerEntity, HasChildEntity<CustomerHouseOwnerDto> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerHouse", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerHouse", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键

    @JsonIgnore
    @Override
    public List<CustomerHouseOwnerDto> getChildren() {
        return getOwners();
    }
}
