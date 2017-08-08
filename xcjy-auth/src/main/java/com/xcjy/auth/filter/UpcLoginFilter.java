package com.xcjy.auth.filter;

import com.xcjy.auth.service.AuthMessageService;
import com.xcjy.auth.token.UpcToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcLoginFilter extends UpcAuthFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpcLoginFilter.class);

    private static final String USERNAME_IDX = "username";

    private static final String PASSWORD_IDX = "password";

    public UpcLoginFilter(AuthMessageService authMessageService) {
        super(authMessageService);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (super.isAccessAllowed(request, response, mappedValue)) {
            return true;
        }
        String username = WebUtils.toHttp(request).getParameter(USERNAME_IDX);
        String password = WebUtils.toHttp(request).getParameter(PASSWORD_IDX);
        LOGGER.info("get username:{}，password:{}", username, password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }
        UpcToken token = new UpcToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            LOGGER.error("login fail:{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 重写请求被拒绝时的处理方式
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }

    @Override
    protected String getMessage() {
        return "登录失败，用户名或密码错误";
    }
}
