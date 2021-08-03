package com.ccicnet.gd.customer.task;

import com.ccicnet.gd.common.constant.SystemModule;
import com.ccicnet.gd.common.dto.Dto;
import com.ccicnet.gd.customer.dto.api.CustomerRegisterReq;
import com.ccicnet.gd.customer.dto.api.CustomerRegisterRes;
import com.ccicnet.gd.customer.service.CustomerRegisterService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BatchRegisterTask extends AbstractTask {
    @Resource
    private CustomerRegisterService registerService;

    private TaskParam taskParam;
    private List<CustomerRegisterReq> reqs;
    private List<CustomerRegisterRes> ress;
    private List<String> outTexts;

    @Getter
    @Setter
    private static class TaskParam extends Dto {
        private String fileName;
        private String charset = "UTF-8";
        private char seperator = ',';
        private int headLines = 1;
        private boolean inQuota = false; //输入字段是否带双引号
        private boolean outQuota = false; //输出字段是否带双引号
        private int reqNoCol = 0; //输入文件里，reqNo 所在的列号，0开始计数
        private int certCol = 1; //输入文件里身份证所在的列号，0开始计数
        private int outColumn = 0; //输入文件的哪一列需要打印到输出文件
        private int commitSize = 500;
    }

    @Override
    protected void execute(Long reqId, String param) {
        this.taskParam = Dto.fromJson(param, TaskParam.class);

        reqs = new ArrayList<>(taskParam.commitSize);
        ress = new ArrayList<>(taskParam.commitSize);
        outTexts = new ArrayList<>(taskParam.commitSize);

        try {
            execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void execute() throws IOException {
        File inFile = new File(taskParam.fileName);
        if (!inFile.exists()) {
            log.error("file {} not exists.", taskParam.fileName);
            return;
        }

        String outFileName = taskParam.fileName + ".out";
        File outFile = new File(outFileName);

        log.info("Processing lines...");

        try (LineIterator iterator = FileUtils.lineIterator(inFile, taskParam.charset);

             FileOutputStream fos = new FileOutputStream(outFile, false);
             OutputStreamWriter osw = new OutputStreamWriter(fos, taskParam.charset);
             BufferedWriter bufferedWriter = new BufferedWriter(osw);
        ) {

            int lineCount = 0;
            while (iterator.hasNext()) {
                String line = iterator.nextLine();
                lineCount++;

                if (lineCount <= taskParam.headLines) {
                    continue;
                }

                String[] columns = StringUtils.splitPreserveAllTokens(line, taskParam.seperator);
                if (columns.length <= taskParam.certCol || columns.length <= taskParam.reqNoCol) {
                    log.error("line {} error.", lineCount);
                    continue;
                }

                CustomerRegisterReq req = buildReq(getInText(columns[taskParam.reqNoCol]), getInText(columns[taskParam.certCol]));
                reqs.add(req);
                outTexts.add(getInText(columns[taskParam.outColumn]));

                if (lineCount % taskParam.commitSize == 0) {
                    process(bufferedWriter);
                    log.info("{} lines processed.", lineCount);
                }
            }

            process(bufferedWriter);//确保最后的非整批的数据也被处理

            log.info("{} lines processed. Task finished.", lineCount);
        }
    }

    private void process(BufferedWriter bufferedWriter) throws IOException {
        registerService.register(reqs, ress);

        for (int i = 0; i < ress.size(); i++) {
            if (ress.get(i).getCustomerId() != null) {
                bufferedWriter.write(getOutText(outTexts.get(i)) + taskParam.seperator + getOutText(ress.get(i).getCustomerId().toString()));
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();

        reqs.clear();
        ress.clear();
        outTexts.clear();
    }

    private CustomerRegisterReq buildReq(String reqNo, String certId) {
        CustomerRegisterReq req = new CustomerRegisterReq();
        req.setSystemCode(SystemModule.KHGL.getCode());
        req.setReqNo(reqNo);
        req.setCertId(certId);
        return req;
    }

    private String getInText(String column) {
        return taskParam.inQuota ? StringUtils.remove(column, '"') : column;
    }

    private String getOutText(String column) {
        return taskParam.outQuota ? ('"' + column + '"') : column;
    }
}
