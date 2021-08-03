package com.ccicnet.gd.customer.util;

import com.ccicnet.gd.common.environment.DateUtil;
import com.ccicnet.gd.customer.constant.TableColumnTypeEnum;
import com.ccicnet.gd.customer.entity.base.TableColumnEntity;
import com.sun.mail.imap.protocol.BODY;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Acmen
 * @ClassName TableColumnUtil.java
 * @Description TODO
 * @CreateTime 2020年11月03日 13:38:20
 */
@Component
@Slf4j
public class TableColumnUtil {
    @Value("${spring.jpa.hibernate.naming.physical-strategy}")
    private String namingStrategy;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static JdbcTemplate jdbcTemplateS;

    private static String namingStrategyS;

    private static Map<String,List<TableColumnEntity>> tableColumnsMap;

    @PostConstruct
    public void init(){
        this.namingStrategyS = namingStrategy;
        this.jdbcTemplateS = jdbcTemplate;
        loadTableColumnInfo();
    }

    /**
     * 检查源字段值和类型是否满足目标数据库字段插入要求
     * @param sourceValue 原值
     * @param targetClass 目标类
     * @param targetPd 目标字段
     * @return
     */
    public static boolean checkType(Object sourceValue,Class targetClass,PropertyDescriptor targetPd){
        if (!(sourceValue instanceof Number
                || sourceValue instanceof Date
                || sourceValue instanceof String)){
            return Boolean.TRUE;
        }

        TableColumnEntity targetType = getTableColumns(targetClass,targetPd);
        if (ObjectUtils.isEmpty(sourceValue)){
            return Boolean.FALSE;
        }
        if (sourceValue instanceof Number){
            return checkNumber(sourceValue,targetType);
        }
        if (sourceValue instanceof Date){
            return checkDate(sourceValue,targetType);
        }
        if (sourceValue instanceof String){
            return checkString(sourceValue,targetType);
        }
        return Boolean.FALSE;
    }

    /**
     * 检查int，double，BigDecimal等数字类型是否可以存到数据库中对应字段
     * @param sourceValue
     * @param targetType
     * @return
     */
    public static boolean checkNumber(Object sourceValue,TableColumnEntity targetType){
        int length = 0;
        if (TableColumnTypeEnum.NUMBER.match(targetType.getDataType())){
            if (sourceValue.toString().indexOf(".") > 0){
                length = StringUtils.split(sourceValue.toString(),".")[0].length();
            }
            else {
                length = sourceValue.toString().length();
            }
            if (targetType.getDataPrecision() == null ){
                return Boolean.FALSE;
            }
            if (targetType.getDataScale() == null){
                targetType.setDataScale(0);
            }

            return length <= (targetType.getDataPrecision() - targetType.getDataScale());

        }
        if (TableColumnTypeEnum.NVARCHAR2.match(targetType.getDataType()) || TableColumnTypeEnum.VARCHAR2.match(targetType.getDataType())){
            length = sourceValue.toString().length();
            if (targetType.getDataLength() == null ){
                return Boolean.FALSE;
            }
            return length <= targetType.getDataLength();
        }
        return Boolean.FALSE;
    }

    /**
     * 检查DATE类型是否可以存到数据库中对应字段
     * @param sourceValue
     * @param targetType
     * @return
     */
    public static boolean checkDate(Object sourceValue,TableColumnEntity targetType){
        int length = 0;
        if (TableColumnTypeEnum.DATE.match(targetType.getDataType())){
            return Boolean.TRUE;
        }
        if (TableColumnTypeEnum.NVARCHAR2.match(targetType.getDataType()) || TableColumnTypeEnum.VARCHAR2.match(targetType.getDataType())){
            length = sourceValue.toString().length();
            if (targetType.getDataLength() == null ){
                return Boolean.FALSE;
            }
            return length <= targetType.getDataLength();
        }
        return Boolean.FALSE;
    }

    /**
     * 检查字符串类型是否可以存到数据库中对应字段
     * @param sourceValue
     * @param targetType
     * @return
     */
    public static boolean checkString(Object sourceValue,TableColumnEntity targetType){
        int length = 0;
        if (targetType.getDataLength() == null ){
            return Boolean.FALSE;
        }
        //nvarchar2类型1个长度可以存储一个汉字，数字或英文
        if (TableColumnTypeEnum.NVARCHAR2.match(targetType.getDataType())){
            length = sourceValue.toString().toCharArray().length;
            return length <= targetType.getDataLength();
        }
        //varchar类型中，汉字占2字节，数字英文占1字节
        if (TableColumnTypeEnum.VARCHAR2.match(targetType.getDataType())){
            char[] chars = sourceValue.toString().toCharArray();
            length = 0;
            for (char c : chars){
                if (Integer.toBinaryString(c).length() > 8){
                    length += 2;
                }
                else {
                    length ++;
                }
            }
            return length <= targetType.getDataLength();
        }
        if (TableColumnTypeEnum.DATE.match(targetType.getDataType())){
            try {
                DateUtil.fromString(sourceValue.toString());
                return Boolean.TRUE;
            }
            catch (Exception e){
                log.warn("{}尝试转换为Date类型时失败",sourceValue,e);
            }
        }
        if (TableColumnTypeEnum.NUMBER.match(targetType.getDataType())){
            try {
                NumberUtils.parseNumber(sourceValue.toString(),BigDecimal.class);
                return Boolean.TRUE;
            }
            catch (Exception e){
                log.warn("{}尝试转换为BigDecimal类型时失败",sourceValue,e);
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 加载数据库表信息
     */
    private static void loadTableColumnInfo(){
        log.info("加载数据库表信息 -----------------> Start");
        tableColumnsMap = new HashMap<>();
        Reflections f = new Reflections("com.ccicnet.gd.customer.entity");
        Set<Class<?>> classSet = f.getTypesAnnotatedWith(Table.class);
        for( Class cl : classSet){
            String tableName = getTableName(cl);
            List<TableColumnEntity> tableColumnEntityList = getTableColumns(tableName);
            tableColumnsMap.put(tableName,tableColumnEntityList);
            log.info("加载数据库表信息，【{}】 -----------------> Success",tableName);
        }
        log.info("加载数据库表信息，共计 {} 张表 -----------------> End",tableColumnsMap.size());
    }

    /**
     * 根据类和属性获取对应表的字段信息
     * @param clazz
     * @param propertyDescriptor
     * @return
     */
    public static TableColumnEntity getTableColumns(Class<?> clazz,PropertyDescriptor propertyDescriptor){
        String tableName = getTableName(clazz);
        if (StringUtils.isEmpty(tableName) || !tableColumnsMap.containsKey(tableName)){
            return null;
        }
        List<TableColumnEntity> tableColumnEntityList = tableColumnsMap.get(tableName);
        String columnName = getColumnName(propertyDescriptor);
        if (StringUtils.isEmpty(columnName)){
            return null;
        }
        return tableColumnEntityList
                .stream().filter(x->x.getColumnName().equals(columnName))
                .findFirst().orElse(null);
    }

    /**
     * 根据表名获取表的列信息
     * @param tableName 表名
     * @return
     */
    public static List<TableColumnEntity> getTableColumns(String tableName){
        StringBuilder sqlBuilder = new StringBuilder("select ");
        sqlBuilder.append(" column_name as columnName, ");
        sqlBuilder.append(" data_type as dataType, ");
        sqlBuilder.append(" data_length as dataLength, ");
        sqlBuilder.append(" data_precision as dataPrecision, ");
        sqlBuilder.append(" data_scale as dataScale ");
        sqlBuilder.append(" from USER_TAB_COLS t ");
        sqlBuilder.append(" where TABLE_NAME = '" + tableName.toUpperCase() + "' ");
        sqlBuilder.append(" order by column_id ");
        return jdbcTemplateS.queryForList(sqlBuilder.toString()).stream()
                .map(x->{
                    TableColumnEntity entity = new TableColumnEntity();
                    if (!ObjectUtils.isEmpty(x.get("columnName"))){
                        entity.setColumnName(x.get("columnName").toString());
                    }
                    if (!ObjectUtils.isEmpty(x.get("dataLength"))){
                        entity.setDataLength(((BigDecimal) x.get("dataLength")).intValue());
                    }
                    if (!ObjectUtils.isEmpty(x.get("dataType"))){
                        entity.setDataType(x.get("dataType").toString());
                    }
                    if (!ObjectUtils.isEmpty(x.get("dataPrecision"))){
                        entity.setDataPrecision(((BigDecimal) x.get("dataPrecision")).intValue());
                    }
                    if (!ObjectUtils.isEmpty(x.get("dataScale"))){
                        entity.setDataScale(((BigDecimal) x.get("dataScale")).intValue());
                    }
                    return entity;
                }).collect(Collectors.toList());
    }
    /**
     * 获取表名
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static String getTableName(Class<?> clazz) {
        try {
            PhysicalNamingStrategy strategy = (PhysicalNamingStrategy) Class.forName(namingStrategyS).newInstance();
            Table annotation = clazz.getAnnotation(Table.class);
            if (annotation == null) {
                return strategy.toPhysicalTableName(Identifier.toIdentifier(clazz.getSimpleName()), null).toString().toUpperCase();
            } else {
                return annotation.name().toUpperCase();
            }
        }
        catch (Exception e){
            log.error("获取表名失败",e);
            return "";
        }
    }

    /**
     * 获取列名
     * @param propertyDescriptor
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static String getColumnName(PropertyDescriptor propertyDescriptor) {
        try {
            PhysicalNamingStrategy strategy = (PhysicalNamingStrategy) Class.forName(namingStrategyS).newInstance();
            Column annotation = propertyDescriptor.getPropertyType().getAnnotation(Column.class);
            if (annotation == null) {
                return strategy.toPhysicalColumnName(Identifier.toIdentifier(propertyDescriptor.getName()), null).toString().toUpperCase();
            } else {
                return annotation.name().toUpperCase();
            }
        }
        catch (Exception e){
            log.error("获取列名失败",e);
            return "";
        }
    }
}
