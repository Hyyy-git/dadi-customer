package com.ccicnet.gd.customer.controller;

import com.ccicnet.gd.common.dto.Response;
import com.ccicnet.gd.customer.dao.SeqGen;
import com.ccicnet.gd.customer.service.config.ConfigCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 配置接口
 */
@RestController
@Slf4j
@RequestMapping("/config")
public class ConfigController {
    @Resource
    private ConfigCache configCache;

    @Resource
    private SeqGen seqGen;

    @GetMapping("seq/nextval")
    @ResponseBody
    public Response<Long> getSeqNextValue() {
        log.info("getSeqNextValue");
        Long res = seqGen.getSeqNextValue();
        log.debug("getSeqNextValue={}", res);
        log.info("getSeqNextValue");
        return Response.of(res);
    }

    @GetMapping("alarm/reload")
    @ResponseBody
    public Response<Void> reloadAlarm() {
        log.info("to do reloadAlarm");
        configCache.loadAlarm();
        log.info("done reloadAlarm");
        return Response.of(null);
    }
}
