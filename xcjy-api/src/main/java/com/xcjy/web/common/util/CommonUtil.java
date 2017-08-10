package com.xcjy.web.common.util;

import com.xcjy.web.common.enums.RoleEnum;
import org.apache.shiro.util.CollectionUtils;

import java.util.List;

/**
 * Created by tupeng on 2017/8/6.
 */
public abstract class CommonUtil {

    public static String getRolIdString(List<RoleEnum> roleEnums) {
        if (CollectionUtils.isEmpty(roleEnums)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        roleEnums.forEach(roleEnum -> builder.append(roleEnum.name()).append(","));
        return builder.toString().substring(0, builder.toString().length() - 1);
    }

}
