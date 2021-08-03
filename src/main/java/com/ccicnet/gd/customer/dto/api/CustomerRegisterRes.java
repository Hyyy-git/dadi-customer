package com.ccicnet.gd.customer.dto.api;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.dto.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 获取客户ID 响应报文
 */
@Getter
@Setter
@AllArgsConstructor
public class CustomerRegisterRes extends Dto {
    private static final long serialVersionUID = 1L;

    private Long customerId;
    private Boolean isNew;
}
