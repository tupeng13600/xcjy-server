package com.xcjy.auth.filter;

import com.google.gson.Gson;
import com.xcjy.auth.cache.AuthCache;
import com.xcjy.auth.cache.TokenThreadLocal;
import com.xcjy.auth.model.RespModel;
import com.xcjy.auth.token.UpcToken;
import com.xcjy.auth.util.UpcSecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcAuthFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpcAuthFilter.class);

    protected static String TOKEN_NAME = "Access-Token";

    protected static String TOKEN_COOKIE_NAME = "JSESSIONID";

    protected static final String USERNAME_IDX = "username";

    protected static final String PASSWORD_IDX = "password";

    private static ThreadLocal<String> filterThreadLocal = new ThreadLocal<>();

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
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
        String token = getToken(WebUtils.toHttp(request));
        return login(WebUtils.toHttp(request), token) ? true : onAccessDenied(WebUtils.toHttp(request), WebUtils.toHttp(response), filterThreadLocal.get());
    }

    private boolean login(HttpServletRequest request, String token) {
        UpcToken upcToken = getUpcToken(token);
        if (null == upcToken) {
            upcToken = getUpcToken(request);
        }
        if (null == upcToken) {
            filterThreadLocal.set("用户token无效");
            LOGGER.error("用户token无效，请重新登录：{}", token);
            return false;
        }
        try {
            if (StringUtils.isBlank(token)) {
                token = UpcSecurityUtil.randomString();
            }
            TokenThreadLocal.put(token);
            SecurityUtils.getSubject().login(upcToken);
        } catch (Exception e) {
            filterThreadLocal.set("用户名或密码错误");
            LOGGER.error("登录失败，失败信息:{}", e.getMessage());
            return false;
        }
        return true;
    }

    private UpcToken getUpcToken(String token) {
        return StringUtils.isBlank(token) ? null : AuthCache.get(token);
    }

    private UpcToken getUpcToken(HttpServletRequest request) {
        String username = request.getParameter(USERNAME_IDX);
        String password = request.getParameter(PASSWORD_IDX);
        LOGGER.info("获取到的，用户名:{}，密码:{}", username, password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            filterThreadLocal.set("用户名或者密码不能为空");
            return null;
        }
        return new UpcToken(username, password);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        LOGGER.info("通过Header获取到token：{}", token);
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            LOGGER.info("获取到的cookie大小：{}", null == cookies ? null : cookies.length);
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                        LOGGER.info("通过cookie获取到token：{}", cookie.getValue());
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }

    private Boolean onAccessDenied(HttpServletRequest request, HttpServletResponse response, String message) throws Exception {
        RespModel respModel = new RespModel(false);
        respModel.setData(message);
        String json = new Gson().toJson(respModel);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); //设置编码格式为UTF-8
        response.getWriter().write(json);
        return super.onAccessDenied(request, response);
    }

}
