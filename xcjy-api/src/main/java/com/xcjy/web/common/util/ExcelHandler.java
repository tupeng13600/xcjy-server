package com.xcjy.web.common.util;

import com.xcjy.web.common.exception.EducationException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by tupeng on 2017/7/24.
 */
public class ExcelHandler {

    public static <T> T getExcelObject(Class<T> clss, Map<String, String> excelData) {
        Method[] methods = clss.getDeclaredMethods();
        T result = getInstance(clss);
        if (null == result || null == methods || methods.length < 0) {
            return result;
        }

        for (Method method : methods) {
            ExcelData annotation = method.getAnnotation(ExcelData.class);
            if (null != annotation) {
                String data = excelData.get(annotation.columnName());
                String parseMethodName = annotation.parseMethod();
                if (StringUtils.isBlank(parseMethodName)) {
                    invoke(method, result, data);
                } else {
                    Method parseMethod = getMethod(clss, parseMethodName);
                    Object parseData = invoke(parseMethod, result, data);
                    invoke(method, result, parseData);
                }
            }
        }
        return result;
    }

    private static Method getMethod(Class clss, String methodName) {
        try {
            return clss.getDeclaredMethod(methodName, String.class);
        } catch (NoSuchMethodException e) {
            throw new EducationException("方法：" + methodName + " 不存在");
        }
    }

    private static <T> Object invoke(Method method, T result, Object args) {
        try {
            return method.invoke(result, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new EducationException("调用方法失败，方法名：" + method.getName() + "，参数：" + args);
        }
    }

    private static <T> T getInstance(Class<T> clss) {
        try {
            return clss.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new EducationException("无法创建对象实例：" + clss);
        }
    }

}
