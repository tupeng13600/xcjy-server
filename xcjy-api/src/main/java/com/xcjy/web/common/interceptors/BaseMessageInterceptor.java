package com.xcjy.web.common.interceptors;


import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.xcjy.web.common.cache.CacheFactory.cacheIdUsers;
import static com.xcjy.web.common.cache.CacheFactory.cacheUsernameUsers;
import static com.xcjy.web.common.cache.CacheFactory.usernameUsers;

/**
 * Created by tupeng on 2017/7/22.
 */
@Component("baseMessageInterceptor")
public class BaseMessageInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(BaseMessageInterceptor.class);

    @Autowired
    private UserService userService;

    /**
     * 将schoolId写进ThreadLocal
     * 缓存用户基本信息
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String username = null;
        try {
            username = UserUtil.getCurrentUserName();
        } catch (Exception e) {
            logger.warn("can not get user info");
        }
        if (StringUtils.isNotBlank(username)) {
            UserModel user = this.getUser(username);
            if (null != user) {
                CurrentThreadLocal.setSchoolId(user.getSchoolId());
            }
        }
        return true;
    }

    /**
     * 获取用户，并且将用户缓存到缓存
     *
     * @param username
     * @return
     */
    private UserModel getUser(String username) {
        UserModel userModel = usernameUsers.get(username);
        if (null == userModel) {
            User user = userService.getByUsernameOrPhone(username, username);
            if (null == user) {
                throw new EducationException("用户:" + username + " 不存在");
            }
            cacheUsernameUsers(user);
            cacheIdUsers(user);
        }
        return usernameUsers.get(username);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
