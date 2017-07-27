package com.xcjy.web.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tupeng on 2017/7/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface ExcelData {

    String columnName() default "";

    /**
     * excel获取的所有数据都是string，故需要解析器
     * @return
     */
    String parseMethod() default "";

}
