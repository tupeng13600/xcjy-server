package com.xcjy.web.common.interceptors;

import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.util.ReflectUtil;
import com.xcjy.web.controller.req.PageReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by tupeng on 2017/7/22.
 * <p>
 * mybatis 拦截器，统一对sql进行处理
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisQueryInterceptors implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(MybatisQueryInterceptors.class);

    private static final String whereCondition = " WHERE deleted = FALSE";

    private static final String andCondition = " AND deleted = FALSE";

    private static final String andSchoolIdCondition = " AND school_id = ";

    private static final String whereSchoolIdCondition = " WHERE school_id = ";

    private static final String orderCondition = "order by";

    private static final String groupCondition = "group by";

    private static final String limitCondition = " limit ";

    private static final Integer defaultPageSize = 10;

    public static final String
            [] deletedMatches = new String[]{"wheredeleted=false", "anddeleted=false"};

    public static final String sqlSuffix = ";";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getProperty(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        if (isSelect(boundSql.getSql())) {
            String sql = addLimit(appendDeleted(appendSchoolId(replaceEndOfSql(boundSql.getSql()))));
            ReflectUtil.setProperty(boundSql, "sql", sql);
            logger.debug("开始执行sql : {}", sql);
        }
        return invocation.proceed();
    }

    /**
     * 添加分页操作
     *
     * @param sql
     * @return
     */
    private String addLimit(String sql) {
        PageReq pageReq = CurrentThreadLocal.getPageReq();
        CurrentThreadLocal.removePageReq();
        if (null != pageReq && !sql.toLowerCase().contains(limitCondition)) {
            Integer pageStart = getPageStart(pageReq.getPage(), pageReq.getPageSize());
            Integer pageEnd = getPageSize(pageReq.getPageSize());
            StringBuilder builder = new StringBuilder();
            return builder.append(sql).append(limitCondition).append(pageStart).append(", ").append(pageEnd).toString();
        }
        return sql;
    }

    private Integer getPageSize(Integer pageSize) {
        return null == pageSize || pageSize < 1 ? defaultPageSize : pageSize;
    }

    private Integer getPageStart(Integer page, Integer pageSize) {
        if (null == page || page < 1) {
            page = 1;
        }
        pageSize = getPageSize(pageSize);
        return (page - 1) * pageSize;

    }

    private Boolean isSelect(String sql) {
        String des = sql.trim().toLowerCase();
        return des.startsWith("select");
    }

    private String appendSchoolId(String sql) {
        String sqlPrefix = getPrefix(sql);
        String sqlSuffix = getSuffix(sql);
        String schoolId = CurrentThreadLocal.getSchoolId();
        if (StringUtils.isNotBlank(schoolId)) {
            StringBuilder builder = new StringBuilder();
            return sqlPrefix.toLowerCase().contains("where") ?
                    appendEndOfSql(builder.append(sqlPrefix).append(andSchoolIdCondition).append("'")
                            .append(schoolId).append("' ").append(sqlSuffix).toString())
                    : appendEndOfSql(builder.append(sqlPrefix).append(whereSchoolIdCondition).append("'")
                    .append(schoolId).append("' ").append(sqlSuffix).toString());
        }
        return sqlPrefix + sqlSuffix;
    }

    private String getSuffix(String sql) {
        String desSql = sql.toLowerCase();
        String suffix = "";
        if (containsGroup(desSql)) {
            suffix = desSql.toLowerCase().substring(desSql.indexOf(groupCondition), desSql.length());
        } else if (containsOrder(desSql)) {
            suffix = desSql.toLowerCase().substring(desSql.indexOf(orderCondition), desSql.length());
        }
        return suffix;
    }

    private String getPrefix(String sql) {
        String prefix = replaceEndOfSql(sql).toLowerCase();
        if (containsGroup(sql)) {
            prefix = prefix.substring(0, prefix.indexOf(groupCondition));
        } else if (containsOrder(sql)) {
            prefix = prefix.substring(0, prefix.indexOf(orderCondition));
        }
        return prefix;
    }

    private Boolean containsGroup(String sql) {
        String des = sql.toLowerCase();
        return des.contains(groupCondition);
    }

    private Boolean containsOrder(String sql) {
        String des = sql.toLowerCase();
        return des.contains(orderCondition);
    }

    private String appendDeleted(String sql) {
        String sqlPrefix = getPrefix(sql);
        String sqlSuffix = getSuffix(sql);
        if (!matches(sql)) {
            StringBuilder builder = new StringBuilder();
            return sql.toLowerCase().contains("where") ?
                    builder.append(sqlPrefix).append(andCondition).append(" ").append(sqlSuffix).toString()
                    : builder.append(sqlPrefix).append(whereCondition).append(" ").append(sqlSuffix).toString();
        }
        return appendEndOfSql(sqlPrefix + sqlSuffix);
    }

    private String replaceEndOfSql(String sql) {
        return sql.replaceAll(sqlSuffix, "");
    }

    private String appendEndOfSql(String sql) {
        return sql + sqlSuffix;
    }

    private Boolean matches(String sql) {
        String tempSql = sql.replaceAll(" ", "").toLowerCase();
        for (String match : deletedMatches) {
            if (tempSql.contains(match)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
