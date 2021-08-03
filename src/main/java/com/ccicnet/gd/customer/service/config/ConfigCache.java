package com.ccicnet.gd.customer.service.config;

import com.ccicnet.gd.customer.constant.config.ConfigCatalogEnum;
import com.ccicnet.gd.customer.dto.ErrorCondition;
import com.ccicnet.gd.customer.dto.config.ConfigCondition;
import com.ccicnet.gd.customer.entity.BusinessConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 业务配置表的缓存
 */
@Component
public class ConfigCache {
    @Resource
    private ConfigService configService;

    /**
     * 全局异常的业务告警配置。<br/>
     * configKey 对应业务告警平台的发送方式，多种告警方式用逗号分隔。<br/>
     * configValue 是一个 {@link ErrorCondition} 对象，满足这些条件的异常，需要发送到业务告警平台。
     *
     * @see <a href="http://10.130.27.233:8090/pages/viewpage.action?pageId=66628">业务告警平台接口文档</a>
     */
    public static List<BusinessConfig> ALARM_CONFIGS = Collections.emptyList();

    @PostConstruct
    public void init() {
        loadAlarm();
    }

    public void loadAlarm() {
        ALARM_CONFIGS = configService.getConfigs(ConfigCatalogEnum.EXCEPTION_ALARM, new ConfigCondition());
    }
}
