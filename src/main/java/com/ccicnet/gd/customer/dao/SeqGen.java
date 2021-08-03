package com.ccicnet.gd.customer.dao;

import com.ccicnet.gd.common.constant.SystemModule;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SeqGen {
    private final static int START = 0;
    private final static int END = 100000;
    private final static String FORMAT = "%05d";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public Long getSeqNextValue () {
        return jdbcTemplate.queryForObject("select cust_seq.nextval from dual", Long.class);
    }

    /**
     * 返回一个长度为 2+13+5 的基于时间的序号
     */
    public static String value() {
        return String.valueOf(SystemModule.KHGL.getId()) + System.currentTimeMillis() + String.format(FORMAT, RandomUtils.nextInt(START, END));
    }

    /**
     * 返回一串长度为 2+13+5 的基于时间的序号，用于高并发批量执行，
     *
     * @param count 不要超过 {@link #END} - {@link #START} 否则会抛出错误
     */
    public static String[] values(int count) {
        String[] values = new String[count];
        String prefix = String.valueOf(SystemModule.KHGL.getId()) + System.currentTimeMillis();
        int base = RandomUtils.nextInt(START, END - count);
        for (int i = 0; i < count; i++) {
            values[i] = prefix + String.format(FORMAT, base + i);
        }
        return values;
    }
}
