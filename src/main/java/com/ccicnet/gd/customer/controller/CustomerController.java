package com.ccicnet.gd.customer.controller;

import com.ccicnet.gd.common.dto.Response;
import com.ccicnet.gd.customer.dto.api.*;
import com.ccicnet.gd.customer.service.CustomerQueryService;
import com.ccicnet.gd.customer.service.CustomerRegisterService;
import com.ccicnet.gd.customer.service.CustomerUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 客户接口
 */
@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerRegisterService registerService;
    @Resource
    private CustomerQueryService queryService;
    @Resource
    private CustomerUpdateService updateService;

    @PostMapping("register")
    @ResponseBody
    @Transactional
    public Response<CustomerRegisterRes> register(@RequestBody CustomerRegisterReq req) {
        log.info("register req={}", req);
        CustomerRegisterRes res = registerService.register(req);
        log.info("register res={}", res);
        return Response.of(res);
    }

    @PostMapping("update")
    @ResponseBody
    public Response<Void> update(@RequestBody CustomerUpdateReq req) {
        log.info("update req={}", req);
        updateService.update(req);
        log.info("update done. customerId={}, applyNo={}", req.getCustomerId(), req.getApplyNo());
        return Response.of(null);
    }

    @PostMapping("query")
    @ResponseBody
    public Response<CustomerQueryRes> query(@RequestBody CustomerQueryReq req) {
        log.info("query req={}", req);
        CustomerQueryRes res = queryService.query(req);
        log.info("query done. customerId={}, applyNo={}", req.getCustomerId(), req.getApplyNo());
        log.debug("query res={}", res);
        return Response.of(res);
    }
}
