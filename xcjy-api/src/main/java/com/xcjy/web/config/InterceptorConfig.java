package com.xcjy.web.config;

import com.xcjy.web.common.interceptors.BaseMessageInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器初始化
 * Created by tupeng on 2017/7/27.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private BaseMessageInterceptor baseMessageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseMessageInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
