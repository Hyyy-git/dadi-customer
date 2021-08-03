package com.ccicnet.gd.customer.controller;

import com.ccicnet.gd.customer.dto.config.QueryConfigsReq;
import com.ccicnet.gd.customer.entity.BusinessConfig;
import com.ccicnet.gd.customer.service.config.ConfigService;
import com.ccicnet.gd.customer.service.config.ConstantService;
import com.ccicnet.gd.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 前台页面专用入口
 * 由Nginx转发请求到此，可以跨域调用，需要用户登录才能调用这些接口
 *
 * @author Loogle
 * @since 2020/03/14
 */
@RestController
@Slf4j
@RequestMapping("/front")
@CrossOrigin
public class FrontController {
    private ConfigService configService;

    @Resource
    private ConstantService constantService;

    /**
     * 根据枚举的名称（不含package），列举code和description
     */
    @GetMapping("/enums/{name}")
    public Response<List<ConstantService.EnumData>> enums(@PathVariable("name") String name) {
        log.info("enums req={}", name);
        List<ConstantService.EnumData> res = constantService.getDataDetails(name);
        log.debug("enums res={}", res);
        log.info("enums done");
        return Response.of(res);
    }

    /**
     * 批量查询配置表
     */
    @PostMapping("/query/configs")
    public Response<List<BusinessConfig>> queryConfigs(@RequestBody QueryConfigsReq req) {
        log.info("front queryConfigs req={}", req);
        Response<List<BusinessConfig>> res = configService.queryConfigs(req);
        log.debug("front queryConfigs res={}", res);
        log.info("front queryConfigs done");
        return res;
    }
}
