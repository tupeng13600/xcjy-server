package com.xcjy.web.common.util;

import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tupeng on 2017/8/5.
 */
public abstract class CurrentUserUtil {

    public static UserModel currentUser() {
        UserModel userModel = CacheFactory.usernameUsers.get(UserUtil.getCurrentUserName());
        if (null == userModel) {
            throw new EducationException("当前用户信息不存在");
        }
        return userModel;
    }

    public static String currentUserId() {
        return currentUser().getId();
    }

    public static String currentEmployeeId() {
        return currentUser().getEntityId();
    }

    public static Set<RoleEnum> currentRoles() {
        return getRoleEnums(currentUser().getRoleId());
    }

    public static Set<RoleEnum> getRoleEnums(String roleString) {
        if (StringUtils.isNotBlank(roleString)) {
            String[] roleStrs = roleString.split(",");
            List<String> roleList = new ArrayList<>();
            for (String roleStr : roleStrs) {
                roleList.add(roleStr);
            }
            return RoleEnum.getRoleList(roleList);
        }
        return new HashSet<>();
    }

}
