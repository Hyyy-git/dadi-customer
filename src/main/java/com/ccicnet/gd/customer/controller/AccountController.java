package com.ccicnet.gd.customer.controller;

import com.ccicnet.gd.common.dto.Response;
import com.ccicnet.gd.customer.dto.api.*;
import com.ccicnet.gd.customer.entity.CustomerAccount;
import com.ccicnet.gd.customer.service.AccountLoginService;
import com.ccicnet.gd.customer.service.AccountQueryService;
import com.ccicnet.gd.customer.service.AccountRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 账户接口
 */
@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountRegisterService registerService;
    @Resource
    private AccountLoginService loginService;
    @Resource
    private AccountQueryService queryService;

    /**
     * 账户注册
     */
    @PostMapping("register")
    @ResponseBody
    public Response<AccountRegisterRes> register(@RequestBody AccountRegisterReq req) {
        log.info("register req={}", req);
        AccountRegisterRes res = registerService.register(req);
        log.info("register res={}", res);
        return Response.of(res);
    }

    /**
     * 账户登录
     */
    @PostMapping("login")
    @ResponseBody
    public Response<Void> login(@RequestBody AccountLoginReq req) {
        log.info("login req={}", req);
        loginService.login(req);
        log.debug("login done");
        return Response.of(null);
    }

    /**
     * 账户销户
     */
    @PostMapping("close")
    @ResponseBody
    public Response<Void> close(@RequestBody AccountCloseReq req) {
        log.info("close req={}", req);
        registerService.close(req);
        log.info("close done");
        return Response.of(null);
    }

    @PostMapping("query")
    @ResponseBody
    public Response<CustomerAccount> query(@RequestBody AccountQueryReq req) {
        log.info("query req={}", req);
        CustomerAccount res = queryService.query(req);
        log.debug("query res={}", res);
        log.debug("query done");
        return Response.of(res);
    }
}
