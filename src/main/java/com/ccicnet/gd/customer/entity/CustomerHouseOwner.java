package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.customer.dto.modal.CustomerHouseOwnerDto;
import com.ccicnet.gd.customer.entity.base.HasParentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户房产产权人信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_house_owner")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerHouseOwner extends CustomerHouseOwnerDto implements BaseEntity<Long, Date>, HasParentEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerHouseOwner", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerHouseOwner", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    @JsonIgnore
    private Long id; //主键

    @JsonIgnore
    @Column(name = "house_id", nullable = false)
    private Long parentId;
}
