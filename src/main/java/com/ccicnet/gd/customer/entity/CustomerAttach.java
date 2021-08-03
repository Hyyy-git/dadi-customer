package com.ccicnet.gd.customer.entity;

import com.ccicnet.gd.customer.dto.modal.CustomerAttachDto;
import com.ccicnet.gd.customer.dto.modal.CustomerAttachImageDto;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 客户附件
 */
@Entity
@Getter
@Setter
@Table(name = "cust_attach")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CustomerAttach extends CustomerAttachDto implements CustomerEntity, HasChildEntity<CustomerAttachImageDto> {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "CustomerAttach", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CustomerAttach", sequenceName = "CUST_SEQ", allocationSize = 1)
    @Id
    private Long id;

    @JsonIgnore
    @Override
    public List<CustomerAttachImageDto> getChildren() {
        return getImages();
    }
}
