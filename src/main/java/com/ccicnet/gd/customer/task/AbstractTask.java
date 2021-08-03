package com.ccicnet.gd.customer.task;

import com.ccicnet.gd.common.environment.log.LogContext;
import com.ccicnet.gd.customer.dto.ErrorCondition;
import com.ccicnet.gd.common.dto.Dto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 所有定时任务的基础类
 */
@Slf4j
public abstract class AbstractTask {
    @Setter
    @Getter
    public static class GeneralParam extends Dto {
        private static final long serialVersionUID = 1L;
        private String businessDate;   //业务日期
        private AutoRetry autoRetry;    //遇到异常后，是否自动重试

        @Setter
        @Getter
        public static class AutoRetry extends ErrorCondition {
            private Integer intervalSeconds = 20; //间隔几秒重试一次
            private Integer maxRetryNum = 100;    //最大重试次数
        }
    }

    @LogContext
    protected void run(Long reqId, String param) {
        String taskName = getClass().getSimpleName();
        log.info("begin taskName={}, reqId={}, param={}", taskName, reqId, param);

        GeneralParam taskParam = Dto.fromJson(param, GeneralParam.class); //解析基本参数用于当前基类

        int retryNum = 0;
        while (true) {
            try {
                log.info("{} execute begin...", taskName);
                execute(reqId, param);
                log.info("{} execute end.", taskName);
                break; //无异常就直接退出，无需重试
            } catch (Exception e) {
                log.error("{} run fail. reqId={}, param={}", taskName, reqId, param, e);

                try {
                    onException(e);
                } catch (Exception e1) {
                    log.error("{} run fail onException", taskName, e1);
                }

                retryNum = getRetryNum(e, taskParam, retryNum);
                if (retryNum == 0) {
                    break;
                }
            }
        }

        log.info("end taskName={}, reqId={}", taskName, reqId);
    }

    private int getRetryNum(Exception e, GeneralParam taskParam, int retryNum) {
        if (taskParam.autoRetry == null) {
            log.info("Do not need auto retry");
            return 0;
        }

        if (retryNum >= taskParam.autoRetry.maxRetryNum) {
            log.error("Reach max retry num. taskName={}, maxRetryNum={}", getClass().getSimpleName(), taskParam.autoRetry.maxRetryNum);
            return 0;
        }

        if (!taskParam.autoRetry.matches(e)) {
            log.info("Regex does not match the Exception, no retry.");
            return 0;
        }

        try {
            log.info("taskName={}, retryNum={}, maxRetryNum={}. To sleep {} seconds before retry...",
                    getClass().getSimpleName(), retryNum, taskParam.autoRetry.maxRetryNum, taskParam.autoRetry.intervalSeconds);

            Thread.sleep(taskParam.autoRetry.intervalSeconds * 1000);
        } catch (InterruptedException e1) {
            log.warn("Task end with InterruptedException. {}", e.getMessage());
        }

        return ++retryNum;
    }


    /**
     * 具体的实现类的参数类型可能不一样，需要把param传递到子类以便重新解析参数。
     */
    protected abstract void execute(Long reqId, String param);

    protected void onException(Exception e) {
    }
}
