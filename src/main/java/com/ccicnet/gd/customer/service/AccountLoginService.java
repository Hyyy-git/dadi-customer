package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.dao.CustomerAccountDao;
import com.ccicnet.gd.customer.dto.api.AccountLoginReq;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountLoginService {
    @Resource
    private CustomerAccountDao accountDao;

    public void login(AccountLoginReq req) {
        CustomerAccount account = accountDao.getOneByPhoneNo(req.getPhoneNo());
        if (account.getClosed()) {
            throw new BusinessException(BizError.ACCOUNT_CLOSED);
        }
    }
}