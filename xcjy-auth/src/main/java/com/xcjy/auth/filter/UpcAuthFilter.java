package com.xcjy.auth.filter;

import com.google.gson.Gson;
import com.xcjy.auth.model.RespModel;
import com.xcjy.auth.model.UpcLoginSuccessModel;
import com.xcjy.auth.service.AuthMessageService;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcAuthFilter extends BasicHttpAuthenticationFilter {

    private AuthMessageService authMessageService;

    public UpcAuthFilter(AuthMessageService authMessageService) {
        super();
        this.authMessageService = authMessageService;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Boolean isAccessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (isAccessAllowed) {
            authMessageService.saveUserMessage(new UpcLoginSuccessModel(new Date(), WebUtils.toHttp(request).getRemoteHost()));
        }
        return isAccessAllowed;
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
        httpResponse.getWriter().write(json);
        return super.onAccessDenied(request, response);
    }

    protected String getMessage() {
        return "无权限访问";
    }

}
