package com.ccicnet.gd.customer.service.config;

import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.environment.DateUtil;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.constant.config.ConfigCatalogEnum;
import com.ccicnet.gd.customer.dao.BusinessConfigDao;
import com.ccicnet.gd.customer.dto.config.ConfigCondition;
import com.ccicnet.gd.customer.entity.BusinessConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 实现一些无业务属性的配置查询
 */
@Slf4j
public class BaseConfigService {
    @Resource
    private BusinessConfigDao configDao;

    /**
     * 读取配置表信息，并将 configValue 去掉空格后返回
     */
    public String getString(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return String.valueOf(catalog.getDefaultValue());
        else
            return StringUtils.trim(config.getConfigValue());
    }

    public Float getFloat(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return Float.valueOf((String) catalog.getDefaultValue());
        else
            return Float.valueOf(config.getConfigValue());
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 DTO
     */
    @SuppressWarnings("unchecked")
    public <T> T getDto(ConfigCatalogEnum catalog, ConfigCondition cond, Class<T> clazz) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return (T) catalog.getDefaultValue();
        else
            return Dto.fromJson(config.getConfigValue(), clazz);
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 BigDecimal
     */
    public BigDecimal getBigDecimal(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return (BigDecimal) catalog.getDefaultValue();
        else
            return new BigDecimal(config.getConfigValue());
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 int
     */
    public int getInteger(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return (Integer) catalog.getDefaultValue();
        else
            return Integer.parseInt(config.getConfigValue());
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 long
     */
    public long getLong(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return (Long) catalog.getDefaultValue();
        else
            return Long.parseLong(config.getConfigValue());
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 boolean
     */
    public boolean getBool(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null)
            return (Boolean) catalog.getDefaultValue();
        else
            return StringUtils.equalsAnyIgnoreCase(config.getConfigValue(), "y", "yes", "true");
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 List
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Class<T> clazz, ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = getConfig(catalog, cond);
        if (config == null) {
            throw new BusinessException(BizError.CONFIG_ERROR, catalog, cond); //列表类型不支持默认值
        }

        List<T> values = new ArrayList<>();
        for (String val : StringUtils.split(config.getConfigValue(), ',')) {
            val = StringUtils.trim(val);
            Object object;
            if (clazz.isAssignableFrom(Integer.class))
                object = Integer.parseInt(val);
            else if (clazz.isAssignableFrom(Long.class))
                object = Long.parseLong(val);
            else if (clazz.isAssignableFrom(BigDecimal.class))
                object = new BigDecimal(val);
            else if (clazz.isAssignableFrom(Boolean.class))
                object = StringUtils.equalsAnyIgnoreCase(val, "y", "yes", "true");
            else if (clazz.isAssignableFrom(Date.class))
                object = DateUtil.fromString(val);
            else if (clazz.isAssignableFrom(String.class))
                object = val;
            else
                throw new BusinessException(BizError.CONFIG_ERROR, catalog, cond); //不支持的数据类型
            values.add((T) object);
        }

        return values;
    }

    /**
     * 读取配置表信息，并将 configValue 转换成 Set
     */
    public <T> Set<T> getSet(Class<T> clazz, ConfigCatalogEnum catalog, ConfigCondition cond) {
        return new HashSet<>(getList(clazz, catalog, cond));
    }

    /**
     * 查找 business_config 表，返回唯一的一条配置。
     * 用法举例，根据 fundCode + productCode + configKey 查找配置表：
     * <blockquote><pre>
     * getConfig(ConfigCatalogEnum.LOAN_RATE,
     *     new ConfigCondition().fundCode(fundCode).productCode(productCode).configKey(loanTerm))
     * </pre></blockquote>
     */
    public BusinessConfig getConfig(ConfigCatalogEnum catalog, ConfigCondition cond) {
        List<BusinessConfig> configs = getConfigs(catalog, cond);

        if (CollectionUtils.isEmpty(configs)) {
            if (catalog.getDefaultValue() != null) {
                return null;
            }
            throw new BusinessException(BizError.CONFIG_ERROR, catalog, cond);
        }

        BusinessConfig businessConfig = configs.get(0);
        log.info("getConfig result: config.id={}", businessConfig.getId());
        return businessConfig;
    }

    public List<BusinessConfig> getConfigs(ConfigCatalogEnum catalog, ConfigCondition cond) {
        BusinessConfig config = new BusinessConfig();
        config.setCatalogCode(catalog.getCode());
        config.setConfigKey(cond.getConfigKey());

        List<BusinessConfig> list = configDao.findAll(Example.of(config));

        Date sysdate = ObjectUtils.defaultIfNull(cond.getSysDate(), DateUtil.now());
        list.removeIf(item -> !((sysdate.after(item.getValidTime()) || sysdate.equals(item.getValidTime())) && sysdate.before(item.getExpiryTime())));
        return list;
    }
}
