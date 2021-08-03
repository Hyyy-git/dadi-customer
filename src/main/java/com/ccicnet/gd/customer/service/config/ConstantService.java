package com.ccicnet.gd.customer.service.config;

import com.ccicnet.gd.common.constant.BaseEnum;
import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.customer.util.ClassUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 常量相关服务
 * @author lidelu
 * @since 2020/04/02
 */
@Slf4j
@Service
public class ConstantService {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class EnumData extends Dto {
        private static final long serialVersionUID = 1L;
        private Object code;
        private String description;
    }

    private final Map<String, List<EnumData>> enums = new HashMap<>();

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void loadAllEnums() throws IOException, ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        ClassUtil.getClasses("com.ccicnet.gd.customer.constant", BaseEnum.class, list);
        for (Class<?> e : list) {
            addDataDetails((Class<? extends BaseEnum>) e);
        }

        log.info("enumsClass.size={}", enums.size());
    }

    public List<EnumData> getDataDetails(String name) {
        if (enums.containsKey(name)) {
            return enums.get(name);
        } else {
            throw new BusinessException(BizError.DATA_NOT_FOUND, name);
        }
    }

    /**
     * 根据枚举的名称（不含package），列举code和description
     */
    private void addDataDetails(Class<? extends BaseEnum> e) {
        List<EnumData> list = new ArrayList<>();
        for (BaseEnum item : e.getEnumConstants()) {
            list.add(new EnumData(item.getCode(), item.getDescription()));
        }
        enums.put(e.getSimpleName(), list);
    }
}
