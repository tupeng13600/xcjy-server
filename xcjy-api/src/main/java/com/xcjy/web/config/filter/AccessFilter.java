package com.xcjy.web.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tupeng on 2017/7/27.
 */
@WebFilter(filterName="accessFilter",urlPatterns="/**")
@Configuration
public class AccessFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        logger.info("主机：{} 发起请求：方法：{}， uri：{}", httpRequest.getRemoteHost(), httpRequest.getMethod(), httpRequest.getRequestURI());
        String origin = httpRequest.getHeader("Origin");
        if (origin == null) {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        } else {
            logger.info("orign is......{}", origin.toString());
            httpResponse.addHeader("Access-Control-Allow-Origin", origin);
        }
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,PATCH,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,__skcy, No-Cache, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires,  X-E4M-With");
        httpResponse.setHeader("Access-Control-Max-Age", "1728000");

        if ( httpRequest.getMethod().equals("OPTIONS") ) {
            logger.info("OPTIONS 方法。。。。。。。。");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
