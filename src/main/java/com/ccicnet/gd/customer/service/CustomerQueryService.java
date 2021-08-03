package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.customer.dao.*;
import com.ccicnet.gd.customer.dto.api.CustomerQueryReq;
import com.ccicnet.gd.customer.dto.api.CustomerQueryRes;
import com.ccicnet.gd.customer.entity.*;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.ccicnet.gd.customer.entity.base.HasParentEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerQueryService {
    //region DAO
    @Resource
    private CustomerRegistryDao registryDao;
    @Resource
    private CustomerBasicDao basicDao;
    @Resource
    private CustomerAttachDao attachDao;
    @Resource
    private CustomerAttachImageDao attachImageDao;
    @Resource
    private CustomerBankCardDao bankCardDao;
    @Resource
    private CustomerCareerDao careerDao;
    @Resource
    private CustomerContactDao contactDao;
    @Resource
    private CustomerHouseDao houseDao;
    @Resource
    private CustomerHouseOwnerDao houseOwnerDao;
    @Resource
    private CustomerLifeInsuranceDao lifeInsuranceDao;
    @Resource
    private CustomerResidenceDao residenceDao;
    @Resource
    private CustomerVehicleDao vehicleDao;

    @Resource
    private CustomerBasicApplyDao basicApplyDao;
    @Resource
    private CustomerAttachApplyDao attachApplyDao;
    @Resource
    private CustomerAttachImageApplyDao attachImageApplyDao;
    @Resource
    private CustomerBankCardApplyDao bankCardApplyDao;
    @Resource
    private CustomerCareerApplyDao careerApplyDao;
    @Resource
    private CustomerContactApplyDao contactApplyDao;
    @Resource
    private CustomerHouseApplyDao houseApplyDao;
    @Resource
    private CustomerHouseOwnerApplyDao houseOwnerApplyDao;
    @Resource
    private CustomerLifeInsuranceApplyDao lifeInsuranceApplyDao;
    @Resource
    private CustomerResidenceApplyDao residenceApplyDao;
    @Resource
    private CustomerVehicleApplyDao vehicleApplyDao;
    //endregion

    public CustomerQueryRes query(CustomerQueryReq req) {
        Long customerId = req.getCustomerId();
        String applyNo = StringUtils.trimToNull(req.getApplyNo());

        CustomerQueryRes res = new CustomerQueryRes();
        CustomerRegistry registry = registryDao.getRegistry(customerId);
        res.setCertId(registry.getCertId());
        //res.setName(registry.getName());

        if (req.getBasic()) {
            if (applyNo == null) {
                Optional<CustomerBasic> optional = basicDao.findById(customerId);
                optional.ifPresent(res::setBasic);
            } else {
                Optional<CustomerBasicApply> optional = basicApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                optional.ifPresent(res::setBasic);
            }
        }

        if (req.getAttachs()) {
            if (applyNo == null) {
                List<CustomerAttach> entities = attachDao.findByCustomerId(customerId);
                res.setAttachs(entities);
                setChildren(entities, attachImageDao);
            } else {
                List<CustomerAttachApply> entities = attachApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setAttachs(entities);
                setChildren(entities, attachImageApplyDao);
            }
        }

        if (req.getBankCards()) {
            if (applyNo == null) {
                List<CustomerBankCard> entities = bankCardDao.findByCustomerId(customerId);
                res.setBankCards(entities);
            } else {
                List<CustomerBankCardApply> entities = bankCardApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setBankCards(entities);
            }
        }

        if (req.getCareers()) {
            if (applyNo == null) {
                List<CustomerCareer> entities = careerDao.findByCustomerId(customerId);
                res.setCareers(entities);
            } else {
                List<CustomerCareerApply> entities = careerApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setCareers(entities);
            }
        }

        if (req.getContacts()) {
            if (applyNo == null) {
                List<CustomerContact> entities = contactDao.findByCustomerId(customerId);
                res.setContacts(entities);
            } else {
                List<CustomerContactApply> entities = contactApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setContacts(entities);
            }
        }

        if (req.getHouses()) {
            if (applyNo == null) {
                List<CustomerHouse> entities = houseDao.findByCustomerId(customerId);
                res.setHouses(entities);
                setChildren(entities, houseOwnerDao);
            } else {
                List<CustomerHouseApply> entities = houseApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setHouses(entities);
                setChildren(entities, houseOwnerApplyDao);
            }
        }

        if (req.getLifeInsurances()) {
            if (applyNo == null) {
                List<CustomerLifeInsurance> entities = lifeInsuranceDao.findByCustomerId(customerId);
                res.setLifeInsurances(entities);
            } else {
                List<CustomerLifeInsuranceApply> entities = lifeInsuranceApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setLifeInsurances(entities);
            }
        }

        if (req.getResidences()) {
            if (applyNo == null) {
                List<CustomerResidence> entities = residenceDao.findByCustomerId(customerId);
                res.setResidences(entities);
            } else {
                List<CustomerResidenceApply> entities = residenceApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setResidences(entities);
            }
        }

        if (req.getVehicles()) {
            if (applyNo == null) {
                List<CustomerVehicle> entities = vehicleDao.findByCustomerId(customerId);
                res.setVehicles(entities);
            } else {
                List<CustomerVehicleApply> entities = vehicleApplyDao.findByCustomerIdAndApplyNo(customerId, applyNo);
                res.setVehicles(entities);
            }
        }

        return res;
    }

    /**
     * @param parentEntities 父类的entity列表
     * @param childDao       子类的DAO
     * @param <PE>           parent entity class
     * @param <CD>           child dto class
     * @param <CE>           child entity class
     */
    private <PE extends HasChildEntity<CD>, CE extends HasParentEntity & BaseEntity<Long, Date>, CD>
    void setChildren(List<PE> parentEntities, HasParentDao<CE> childDao) {
        for (PE entity : parentEntities) {
            List<CE> children = childDao.findByParentId(entity.getId());
            for (CE child : children) {
                entity.getChildren().add((CD) child); //entity继承自dto，所以直接转换。
            }
        }
    }
}