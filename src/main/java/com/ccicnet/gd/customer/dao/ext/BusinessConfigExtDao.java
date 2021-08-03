package com.ccicnet.gd.customer.dao.ext;

import com.ccicnet.gd.common.dto.PageableReq;
import com.ccicnet.gd.common.util.PageUtil;
import com.ccicnet.gd.customer.dao.BusinessConfigDao;
import com.ccicnet.gd.customer.dto.config.QueryConfigsReq;
import com.ccicnet.gd.customer.entity.BusinessConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
public class BusinessConfigExtDao {
    @Resource
    private BusinessConfigDao configDao;

    private static final int MAX_RANGE_DAYS = 90; //查询的时间跨度不超过的天数
    private static final String RANGE_DAYS_MSG = "起止时间间隔不能超过" + MAX_RANGE_DAYS + "天";

    /**
     * 配置表数据量小，不需要限定查询条件。
     */
    public Page<BusinessConfig> getPageableResult(QueryConfigsReq req) {
        Specification<BusinessConfig> specification = (Specification<BusinessConfig>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (StringUtils.isNotBlank(req.getFundCode()))
                list.add(cb.equal(root.get("fundCode").as(String.class), req.getFundCode()));
            if (StringUtils.isNotBlank(req.getProductCode()))
                list.add(cb.equal(root.get("productCode").as(String.class), req.getProductCode()));
            if (StringUtils.isNotBlank(req.getOrgId()))
                list.add(cb.equal(root.get("orgId").as(String.class), req.getOrgId()));

            if (req.getCatalog() != null)
                list.add(cb.equal(root.get("catalogCode").as(String.class), req.getCatalog().getCode()));
            if (StringUtils.isNotBlank(req.getConfigKey()))
                list.add(cb.equal(root.get("configKey").as(String.class), req.getConfigKey()));
            if (StringUtils.isNotBlank(req.getConfigValue()))
                list.add(cb.like(root.get("configValue").as(String.class), "%" + req.getConfigValue() + "%"));

            if (req.getUpdateTimeBegin() != null)
                list.add(cb.greaterThanOrEqualTo(root.get("updateTime").as(Date.class), req.getUpdateTimeBegin()));
            if (req.getUpdateTimeEnd() != null)
                list.add(cb.lessThanOrEqualTo(root.get("updateTime").as(Date.class), req.getUpdateTimeEnd()));

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };

        PageableReq pageableReq = req.getPage() == null ? new PageableReq() : req.getPage();
        return configDao.findAll(specification, PageUtil.toJpaPageable(pageableReq));
    }
}
