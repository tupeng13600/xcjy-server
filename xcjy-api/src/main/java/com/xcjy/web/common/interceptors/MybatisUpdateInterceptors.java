package com.xcjy.web.common.interceptors;

import com.xcjy.web.common.enums.DbOperationType;
import com.xcjy.web.common.util.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.*;

/**
 * Created by tupeng on 2017/7/22.
 * <p>
 * mybatis 拦截器，统新增操作添加id
 */
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class MybatisUpdateInterceptors implements Interceptor {

    private static final String autoCreateIdKey = "id";
    private static final String createTimeKey = "createTime";
    private static final String updateTimeKey = "updateTime";
    private static final String deletedKey = "deleted";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (null != args && args.length > 0 && isInsert(args)) {
            for (Object arg : args) {
                if (!(arg instanceof MappedStatement)) {
                    if (isBatchInsert(arg)) {
                        initBatchArgs(arg);
                    } else {
                        setInitProperty(arg);
                    }
                }
            }
        }
        return invocation.proceed();
    }

    private void initBatchArgs(Object args){
        Collection des = getBatchArgList(args);
        if(CollectionUtils.isNotEmpty(des)) {
            for (Object arg : des) {
                setInitProperty(arg);
            }
        }
    }

    private Collection getBatchArgList(Object arg) {
        if (arg instanceof Map) {
            Object desParam = ((Map) arg).get("param1");
            if (desParam instanceof Collection) {
                return (Collection) desParam;
            }
        } else if (arg instanceof Collection) {
            return (Collection) arg;
        }
        return null;
    }

    private boolean isBatchInsert(Object arg) {
        if (arg instanceof Map) {
            Object desParam = ((Map) arg).get("param1");
            if (desParam instanceof Collection) {
                return true;
            }
        } else if (arg instanceof Collection) {
            return true;
        }
        return false;
    }

    private void setInitProperty(Object arg) {
        if (null != arg) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            ReflectUtil.setProperty(arg, autoCreateIdKey, id);
            ReflectUtil.setProperty(arg, createTimeKey, new Date());
            ReflectUtil.setProperty(arg, updateTimeKey, new Date());
            ReflectUtil.setProperty(arg, deletedKey, false);
        }
    }

    private Boolean isInsert(Object[] args) {
        if (null != args && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof MappedStatement) {
                    MappedStatement mappedStatement = (MappedStatement) arg;
                    SqlCommandType commandType = mappedStatement.getSqlCommandType();
                    if (commandType.name().equals(DbOperationType.INSERT.name())) {
                        return true;
                    }
                }
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
