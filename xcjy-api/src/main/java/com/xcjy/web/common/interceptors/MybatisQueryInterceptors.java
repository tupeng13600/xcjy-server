package com.xcjy.web.common.interceptors;

import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by tupeng on 2017/7/22.
 * <p>
 * mybatis 拦截器，统一对sql进行处理
 */
@Intercepts({@Signature(method = "query", type = StatementHandler.class, args = {Statement.class, ResultHandler.class})})
public class MybatisQueryInterceptors implements Interceptor {

    public static final String whereCondition = " WHERE deleted = FALSE";

    private static final String andCondition = " AND deleted = FALSE";

    public static final String andSchoolIdCondition = " AND school_id = ";

    public static final String whereSchoolIdCondition = " WHERE school_id = ";

    public static final String
            [] deletedMatches = new String[]{"wheredeleted=false", "anddeleted=false"};

    public static final String sqlSuffix = ";";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getProperty(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        String sql = appendDeleted(appendSchoolId(boundSql.getSql()));
        ReflectUtil.setProperty(boundSql, "sql", sql);
        return invocation.proceed();
    }

    private boolean insert(Invocation invocation) {
        Object[] args = invocation.getArgs();
        if (null != args && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof Map) {
                    Map<String, Object> argMap = (Map<String, Object>) arg;
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    argMap.put("id", id);
                    return true;
                }
            }
        }
        return false;
    }

    private String appendSchoolId(String sql) {
        sql = replaceEndOfSql(sql);
        String schoolId = CurrentThreadLocal.getSchoolId();
        if (StringUtils.isNotBlank(schoolId)) {
            if (sql.toLowerCase().contains("where")) {
                return sql + andSchoolIdCondition + "'" + schoolId + "'";
            } else {
                return sql + whereSchoolIdCondition + "'" + schoolId + "'";
            }
        }
        return sql;
    }

    private String appendDeleted(String sql) {
        sql = replaceEndOfSql(sql);
        if (!matches(sql)) {
            if (sql.toLowerCase().contains("where")) {
                sql += andCondition;
            } else {
                sql += whereCondition;
            }
        }
        return appendEndOfSql(sql);
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
