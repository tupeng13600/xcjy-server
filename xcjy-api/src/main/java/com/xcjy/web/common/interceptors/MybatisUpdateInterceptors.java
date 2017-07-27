package com.xcjy.web.common.interceptors;

import com.xcjy.web.common.enums.DbOperationType;
import com.xcjy.web.util.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

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
                    if (arg instanceof Collection) {
                        Collection collection = (Collection) arg;
                        if (CollectionUtils.isNotEmpty(collection)) {
                            for (Object des : collection) {
                                setInitProperty(des);
                            }
                        }
                    } else {
                        setInitProperty(arg);
                    }
                }
            }
        }
        return invocation.proceed();
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
