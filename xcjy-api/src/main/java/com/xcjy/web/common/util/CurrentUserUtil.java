package com.xcjy.web.common.util;

import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;

/**
 * Created by tupeng on 2017/8/5.
 */
public abstract class CurrentUserUtil {

    public static UserModel currentUser(){
        UserModel userModel = CacheFactory.usernameUsers.get(UserUtil.getCurrentUserName());
        if(null == userModel) {
            throw new EducationException("当前用户信息不存在");
        }
        return userModel;
    }

}
