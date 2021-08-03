package com.ccicnet.gd.customer.service.config;

import com.ccicnet.gd.common.dto.Response;
import com.ccicnet.gd.common.util.PageUtil;
import com.ccicnet.gd.customer.dao.ext.BusinessConfigExtDao;
import com.ccicnet.gd.customer.dto.config.QueryConfigsReq;
import com.ccicnet.gd.customer.entity.BusinessConfig;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配置服务。以后做成 cache
 */
@Service
public class ConfigService extends BaseConfigService {
    @Resource
    private BusinessConfigExtDao configExtDao;

    /**
     * 分页查询配置表
     */
    public Response<List<BusinessConfig>> queryConfigs(QueryConfigsReq req) {
        req.check();
        Page<BusinessConfig> result = configExtDao.getPageableResult(req);
        return Response.of(result.getContent(), PageUtil.getPageRes(result));
    }
}