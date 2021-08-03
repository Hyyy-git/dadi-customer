package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.common.constant.BaseEnum;
import com.ccicnet.gd.common.constant.SystemModule;
import com.ccicnet.gd.customer.dao.CustomerAccountDao;
import com.ccicnet.gd.customer.dao.CustomerRegistryDao;
import com.ccicnet.gd.customer.dto.api.CustomerRegisterReq;
import com.ccicnet.gd.customer.dto.api.CustomerRegisterRes;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import com.ccicnet.gd.customer.entity.CustomerRegistry;
import com.ccicnet.gd.customer.event.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerRegisterService {
    @Resource
    private CustomerAccountDao accountDao;

    @Resource
    private CustomerRegistryDao registryDao;

    @Resource
    private EventProducer customerIdChange;

    public CustomerRegisterRes register(CustomerRegisterReq req) {
        checkReq(req);

        Long customerId;
        boolean isNew;

        Optional<CustomerRegistry> optional = registryDao.findByCertId(req.getCertId());
        if (optional.isPresent()) {
            customerId = optional.get().getId();
            isNew = false;
        } else {
            CustomerRegistry registry = new CustomerRegistry();
            registry.setCertId(req.getCertId());
            registry = registryDao.save(registry);
            customerId = registry.getId();
            isNew = true;
        }

        if (StringUtils.isNotEmpty(req.getPhoneNo())) {
            Optional<CustomerAccount> byPhoneNo = accountDao.findByPhoneNo(req.getPhoneNo());
            if (byPhoneNo.isPresent()) {
                CustomerAccount account = byPhoneNo.get();
                if (account.getCustomerId() == null) {
                    account.setCustomerId(customerId);
                    accountDao.save(account);
                } else if (!account.getCustomerId().equals(customerId)) {
                    log.error("手机号{}对应的客户ID已更换, 旧ID={}, 新ID={}", req.getPhoneNo(), account.getCustomerId(), customerId);
                    customerIdChange.customerIdChange(req.getPhoneNo(), account.getCustomerId(), customerId);
                }
            }
        }

        return new CustomerRegisterRes(customerId, isNew);
    }

    private static final String CERT_ID_REGEX = "^"
            + "\\d{6}" // 6位地区码
            + "(18|19|([23]\\d))\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}" // 3位顺序码
            + "[0-9Xx]" // 校验码
            + "$";

    private void checkReq(CustomerRegisterReq req) {
        Preconditions.checkArgument(StringUtils.isNotBlank(req.getReqNo()), "请求流水号 不能为空");
        Preconditions.checkArgument(BaseEnum.tryParse(SystemModule.class, req.getSystemCode()) != null
                , "系统编码不合法");
        //Preconditions.checkArgument(StringUtils.isNotBlank(req.getName()), "姓名不能为空");
        Preconditions.checkArgument(StringUtils.length(req.getCertId()) == 18, "身份证号码必须为18位");
        Preconditions.checkArgument(req.getCertId().matches(CERT_ID_REGEX), "身份证号码不合法");
    }

    /**
     * 为了启动事务，批量commit。
     */
    @Transactional
    public void register(List<CustomerRegisterReq> reqs, List<CustomerRegisterRes> ress) {
        for (CustomerRegisterReq req : reqs) {
            CustomerRegisterRes res;
            try {
                res = register(req);
            } catch (Exception e) {
                log.error("reqNo={}, certId={}. {}", req.getReqNo(), req.getCertId(), e.getMessage());
                res = new CustomerRegisterRes(null, false);
            }
            ress.add(res);
        }
    }
}