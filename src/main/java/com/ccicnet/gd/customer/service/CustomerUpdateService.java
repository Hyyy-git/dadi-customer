package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.common.constant.BaseEnum;
import com.ccicnet.gd.common.constant.SystemModule;
import com.ccicnet.gd.common.dao.BaseDtDao;
import com.ccicnet.gd.common.entity.BaseEntity;
import com.ccicnet.gd.customer.dao.*;
import com.ccicnet.gd.customer.dto.api.CustomerUpdateReq;
import com.ccicnet.gd.customer.dto.base.CustomerDto;
import com.ccicnet.gd.customer.dto.modal.*;
import com.ccicnet.gd.customer.entity.*;
import com.ccicnet.gd.customer.entity.base.ApplyEntity;
import com.ccicnet.gd.customer.entity.base.CustomerEntity;
import com.ccicnet.gd.customer.entity.base.HasChildEntity;
import com.ccicnet.gd.customer.entity.base.HasParentEntity;
import com.ccicnet.gd.customer.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerUpdateService {
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

    /**
     * 为了启动事务，批量commit。
     */
    @Transactional
    public void update(List<CustomerUpdateReq> reqs) {
        for (CustomerUpdateReq req : reqs) {
            try {
                update(req);
            } catch (Exception e) {
                log.error("reqNo={}, customerId={}. {}", req.getReqNo(), req.getCustomerId(), e.getMessage());
            }
        }
    }

    @Transactional
    public void update(CustomerUpdateReq req) {
        checkReq(req);

        Long customerId = req.getCustomerId();
        String applyNo = req.getApplyNo();

        registryDao.getRegistry(customerId);

        if (req.getBasic() != null) {
            merge(req.getBasic(), basicDao, new CustomerBasic(), customerId);
            merge(req.getBasic(), basicApplyDao, new CustomerBasicApply(), customerId, applyNo);
        }

        if (req.getAttachs() != null) {
            for (CustomerAttachDto dto : req.getAttachs()) {
                merge(dto, attachDao, new CustomerAttach(), customerId, attachImageDao, CustomerAttachImage.class);
                merge(dto, attachApplyDao, new CustomerAttachApply(), customerId, applyNo, attachImageApplyDao, CustomerAttachImageApply.class);
            }
        }

        if (req.getBankCards() != null) {
            for (CustomerBankCardDto dto : req.getBankCards()) {
                merge(dto, bankCardDao, new CustomerBankCard(), customerId);
                merge(dto, bankCardApplyDao, new CustomerBankCardApply(), customerId, applyNo);
            }
        }

        if (req.getCareers() != null) {
            for (CustomerCareerDto dto : req.getCareers()) {
                merge(dto, careerDao, new CustomerCareer(), customerId);
                merge(dto, careerApplyDao, new CustomerCareerApply(), customerId, applyNo);
            }
        }

        if (req.getContacts() != null) {
            for (CustomerContactDto dto : req.getContacts()) {
                merge(dto, contactDao, new CustomerContact(), customerId);
                merge(dto, contactApplyDao, new CustomerContactApply(), customerId, applyNo);
            }
        }

        if (req.getHouses() != null) {
            for (CustomerHouseDto dto : req.getHouses()) {
                merge(dto, houseDao, new CustomerHouse(), customerId, houseOwnerDao, CustomerHouseOwner.class);
                merge(dto, houseApplyDao, new CustomerHouseApply(), customerId, applyNo, houseOwnerApplyDao, CustomerHouseOwnerApply.class);
            }
        }

        if (req.getLifeInsurances() != null) {
            for (CustomerLifeInsuranceDto dto : req.getLifeInsurances()) {
                merge(dto, lifeInsuranceDao, new CustomerLifeInsurance(), customerId);
                merge(dto, lifeInsuranceApplyDao, new CustomerLifeInsuranceApply(), customerId, applyNo);
            }
        }

        if (req.getResidences() != null) {
            for (CustomerResidenceDto dto : req.getResidences()) {
                merge(dto, residenceDao, new CustomerResidence(), customerId);
                merge(dto, residenceApplyDao, new CustomerResidenceApply(), customerId, applyNo);
            }
        }

        if (req.getVehicles() != null) {
            for (CustomerVehicleDto dto : req.getVehicles()) {
                merge(dto, vehicleDao, new CustomerVehicle(), customerId);
                merge(dto, vehicleApplyDao, new CustomerVehicleApply(), customerId, applyNo);
            }
        }
    }

    private <D extends CustomerDto<D>, T extends ApplyEntity>
    void merge(D dto, BaseDtDao<T, Long> dao, T entity, Long customerId, String applyNo) {
        merge(dto, dao, entity, customerId, applyNo, null, null);
    }

    private <D extends CustomerDto<D>, T extends ApplyEntity, CE extends HasParentEntity & BaseEntity<Long, Date>>
    void merge(D dto, BaseDtDao<T, Long> dao, T entity, Long customerId, String applyNo, HasParentDao<CE> childDao, Class<CE> childClass) {
        if (StringUtils.isBlank(applyNo)) {
            return;
        }
        entity.setApplyNo(applyNo);
        merge(dto, dao, entity, customerId, childDao, childClass);
    }

    private <D extends CustomerDto<D>, T extends CustomerEntity>
    void merge(D dto, BaseDtDao<T, Long> dao, T entity, Long customerId) {
        merge(dto, dao, entity, customerId, null, null);
    }

    /**
     * @param dto        父类DTO
     * @param dao        父类DAO
     * @param entity     父类entity
     * @param customerId 客户ID
     * @param childDao   子类DAO
     * @param childClass 子类类型
     * @param <PD>       parent dto class
     * @param <PE>       parent entity class
     * @param <CE>       child entity class
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <PD extends CustomerDto<PD>, PE extends BaseEntity<Long, Date> & CustomerEntity, CE extends BaseEntity<Long, Date> & HasParentEntity>
    void merge(PD dto, BaseDtDao<PE, Long> dao, PE entity, Long customerId, HasParentDao<CE> childDao, Class<CE> childClass) {
        if (dto == null || !dto.hasValidKey()) {
            return;
        }

        entity.setCustomerId(customerId);
        dto.copyKeyTo((PD) entity);
        Optional<PE> optional = dao.findOne(Example.of(entity));
        if (optional.isPresent()) {
            log.info("{} record found with key {}.", entity.getClass().getSimpleName(), entity);
            entity = optional.get();
        }

        ClassUtil.copyProperties(dto, entity, "customerId", "createTime", "updateTime");

        dao.save(entity);

        if (entity instanceof HasChildEntity) {
            try {
                updateChildren((HasChildEntity) entity, childDao, childClass);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param parentEntity 父类entity
     * @param childDao     子类 DAO
     * @param childClass   子类class
     * @param <CD>         child dto class
     * @param <CE>         child entity class
     * @param <PE>         parent entity class
     */
    private <CD, CE extends BaseEntity<Long, Date> & HasParentEntity, PE extends HasChildEntity<CD>>
    void updateChildren(PE parentEntity, HasParentDao<CE> childDao, Class<CE> childClass) throws IllegalAccessException, InstantiationException {
        List<CD> dtos = parentEntity.getChildren();
        if (CollectionUtils.isEmpty(dtos)) {
            return;
        }

        log.info("delete old {} records by parentId {}, and then insert {} new children.", childClass.getSimpleName(), parentEntity.getId(), dtos.size());

        List<CE> oldEntities = childDao.findByParentId(parentEntity.getId());
        childDao.deleteAll(oldEntities);

        List<CE> entities = new ArrayList<>();

        for (CD dto : dtos) {
            if (dto != null) {
                CE entity = childClass.newInstance();
                ClassUtil.copyProperties(dto, entity,"customerId", "createTime", "updateTime");
                entity.setParentId(parentEntity.getId());
                entities.add(entity);
            }
        }
        childDao.saveAll(entities);
    }

    private void checkReq(CustomerUpdateReq req) {
        Preconditions.checkArgument(StringUtils.isNotBlank(req.getReqNo()), "请求流水号 不能为空");
        Preconditions.checkArgument(BaseEnum.tryParse(SystemModule.class, req.getSystemCode()) != null
                , "系统编码不合法");
        Preconditions.checkArgument(req.getCustomerId() != null, "客户编号 不能为空");
    }
}