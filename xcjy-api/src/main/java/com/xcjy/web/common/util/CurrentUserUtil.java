package com.xcjy.web.common.util;

import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

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

    public static String currentName(){
        return currentUser().getName();
    }

    public static Set<RoleEnum> currentRoles() {
        return getRoleEnums(currentUser().getRoleId());
    }

    public static Set<RoleEnum> getRoleEnums(String roleString) {
        if (StringUtils.isNotBlank(roleString)) {
            String[] roleStrs = roleString.split(",");
            List<String> roleList = new ArrayList<>();
            Collections.addAll(roleList, roleStrs);
            return RoleEnum.getRoleList(roleList);
        }
        return new HashSet<>();
    }

}
