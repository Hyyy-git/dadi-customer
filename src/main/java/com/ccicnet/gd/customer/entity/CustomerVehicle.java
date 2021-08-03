package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerVehicleDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户车辆信息
 */
@Entity
@Getter
@Setter
@Table(name = "cust_vehicle")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerVehicle extends CustomerVehicleDto implements CustomerEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerVehicle", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerVehicle", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键

}
