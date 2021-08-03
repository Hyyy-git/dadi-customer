package com.ccicnet.gd.customer.task;

import com.ccicnet.gd.customer.constant.common.BizError;
import com.ccicnet.gd.common.exception.BusinessException;
import com.ccicnet.gd.common.util.ApplicationContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskRunner {

    @Resource(name = "defaultThreadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public void run(Long reqId, String taskName, String param) {
        AbstractTask task = getBean(taskName, AbstractTask.class);
        threadPoolTaskExecutor.execute(() -> task.run(reqId, param));
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        try {
            return ApplicationContextHolder.getBean(beanName, clazz);
        } catch (BeansException e) {
            throw new BusinessException(BizError.FUNCTION_NOT_PROVIDED, beanName);
        }
    }
}
