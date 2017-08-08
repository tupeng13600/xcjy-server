package com.xcjy.auth.filter;

import com.google.gson.Gson;
import com.xcjy.auth.cache.AuthCache;
import com.xcjy.auth.model.RespModel;
import com.xcjy.auth.model.UpcLoginSuccessModel;
import com.xcjy.auth.service.AuthMessageService;
import com.xcjy.auth.token.UpcToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcAuthFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpcAuthFilter.class);

    protected static String TOKEN_NAME = "Access-Token";

    private AuthMessageService authMessageService;

    public UpcAuthFilter(AuthMessageService authMessageService) {
        super();
        this.authMessageService = authMessageService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        initToken(WebUtils.toHttp(request)); //将token存入到
        Boolean isAccessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (isAccessAllowed) {
            authMessageService.saveUserMessage(new UpcLoginSuccessModel(new Date(), WebUtils.toHttp(request).getRemoteHost()));
        }
        return isAccessAllowed;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return super.createToken(request, response);
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
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        RespModel respModel = new RespModel(false);
        respModel.setData(getMessage());
        String json = new Gson().toJson(respModel);
        httpResponse.setContentType("application/json");
        httpResponse.setCharacterEncoding("utf-8"); //设置编码格式为UTF-8
        httpResponse.getWriter().write(json);
        return super.onAccessDenied(request, response);
    }

    protected String getMessage() {
        return "无权限访问";
    }

    protected void initToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            return;
        }
        UpcToken upcToken = AuthCache.getToken(token);
        try {
            SecurityUtils.getSubject().login(upcToken);
        } catch (Exception e) {
            LOGGER.warn("token 登录失败，TOKEN:{}", token);
        }
    }

}
