package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.common.constant.BaseEnum;
import com.ccicnet.gd.common.constant.SystemModule;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.dao.CustomerAccountDao;
import com.ccicnet.gd.customer.dao.CustomerRegistryDao;
import com.ccicnet.gd.customer.dto.api.AccountCloseReq;
import com.ccicnet.gd.customer.dto.api.AccountRegisterReq;
import com.ccicnet.gd.customer.dto.api.AccountRegisterRes;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import com.ccicnet.gd.customer.entity.CustomerRegistry;
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
public class AccountRegisterService {
    @Resource
    private CustomerAccountDao accountDao;
    @Resource
    private CustomerRegistryDao registryDao;

    public AccountRegisterRes register(AccountRegisterReq req) {
        checkReq(req);

        Optional<CustomerAccount> optional = accountDao.findByPhoneNo(req.getPhoneNo());
        if (optional.isPresent()) {
            CustomerAccount account = optional.get();
            if (account.getClosed()) {
                account.setClosed(false);
                accountDao.save(account);
            }

            return new AccountRegisterRes(account.getId(), false);
        }

        CustomerAccount account = new CustomerAccount();
        account.setPhoneNo(req.getPhoneNo());
        account.setChannel(req.getChannel());
        account.setClosed(false);
        account = accountDao.save(account);

        return new AccountRegisterRes(account.getId(), true);
    }

    public void close(AccountCloseReq req) {
        checkReq(req);

        CustomerAccount account = accountDao.getOneByPhoneNo(req.getPhoneNo());
        checkCanClose(req, account);
        account.setClosed(true);
        accountDao.save(account);
    }

    /**
     * 检查能否注销账户
     * TODO: 查询是否有未结清账单，如果有，则不允许注销
     */
    private void checkCanClose(AccountCloseReq req, CustomerAccount account) {
        if (SystemModule.KHAPP.isCode(req.getSystemCode()) && account.getCustomerId() != null) {
            throw new BusinessException(BizError.REALNAME_CANNOT_NOT_CLOSE);
        }
    }

    private static final String PHONE_NO_REGEX = "^[1][3-9][0-9]{9}$";

    private void checkReq(AccountRegisterReq req) {
        Preconditions.checkArgument(StringUtils.isNotBlank(req.getReqNo()), "请求流水号 不能为空");
        Preconditions.checkArgument(BaseEnum.tryParse(SystemModule.class, req.getSystemCode()) != null
                , "系统编码不合法");
        Preconditions.checkArgument(StringUtils.length(req.getPhoneNo()) == 11, "手机号码必须为11位");
        Preconditions.checkArgument(req.getPhoneNo().matches(PHONE_NO_REGEX), "手机号码不合法");
    }

    private void checkReq(AccountCloseReq req) {
        Preconditions.checkArgument(StringUtils.isNotBlank(req.getReqNo()), "请求流水号 不能为空");
        Preconditions.checkArgument(BaseEnum.tryParse(SystemModule.class, req.getSystemCode()) != null
                , "系统编码不合法");
        Preconditions.checkArgument(StringUtils.length(req.getPhoneNo()) == 11, "手机号码必须为11位");
        Preconditions.checkArgument(req.getPhoneNo().matches(PHONE_NO_REGEX), "手机号码不合法");
    }

    /**
     * 为了启动事务，批量commit。
     */
    @Transactional
    public void register(List<AccountRegisterReq> reqs, List<AccountRegisterRes> ress) {
        for (AccountRegisterReq req : reqs) {
            AccountRegisterRes res;
            try {
                res = register(req);
            } catch (Exception e) {
                log.error("reqNo={}, phoneNo={}. {}", req.getReqNo(), req.getPhoneNo(), e.getMessage());
                res = new AccountRegisterRes(null, false);
            }
            ress.add(res);
        }
    }
}