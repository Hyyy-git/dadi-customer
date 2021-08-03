package com.ccicnet.gd.customer.service;

import com.ccicnet.gd.customer.dao.CustomerAccountDao;
import com.ccicnet.gd.customer.dto.api.AccountQueryReq;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountQueryService {
    @Resource
    private CustomerAccountDao accountDao;

    public CustomerAccount query(AccountQueryReq req) {
        checkReq(req);
        CustomerAccount account;
        if (req.getAccountId() != null) {
            account = accountDao.getOneById(req.getAccountId());
        } else {
            account = accountDao.getOneByPhoneNo(req.getPhoneNo());
        }
        return account;
    }

    private void checkReq(AccountQueryReq req) {

    }
}