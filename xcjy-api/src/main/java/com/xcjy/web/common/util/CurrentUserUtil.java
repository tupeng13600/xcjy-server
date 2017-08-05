package com.xcjy.web.common.util;

import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.model.UserModel;

/**
 * Created by tupeng on 2017/8/5.
 */
public abstract class CurrentUserUtil {

    public static UserModel currentUser(){
        return CacheFactory.usernameUsers.get(UserUtil.getCurrentUserName());
    }

}
