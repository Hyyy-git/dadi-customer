package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerVehicleDto;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
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
@Table(name = "cust_vehicle_apply")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerVehicleApply extends CustomerVehicleDto implements ApplyEntity {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerVehicleApply", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerVehicleApply", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id; //主键
    private String applyNo; //申请号
}
