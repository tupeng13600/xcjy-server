package com.xcjy.web.config;


import com.xcjy.auth.UpcFilterFactoryBean;
import com.xcjy.auth.manager.UpcSecurityManager;
import com.xcjy.auth.service.AuthMessageService;
import com.xcjy.web.common.auth.AuthService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tupeng on 2017/7/27.
 * 拦截器相关配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");// 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }

    @Bean("authService")
    public AuthService authService() {
        return new AuthService();
    }

    @Bean("securityManager")
    public UpcSecurityManager securityManager(AuthMessageService authService) {
        return new UpcSecurityManager(authService);
    }


    @Bean("shiroFilter")
    public UpcFilterFactoryBean shiroFilter(UpcSecurityManager securityManager, AuthMessageService authService) {
        return new UpcFilterFactoryBean(securityManager, authService, getDefineFilterChain());
    }

    /**
     * 在这里添加shiro的拦截路径
     * 例如：defineFilterChain.add("/user/info = anon");
     * @return
     */
    private List<String> getDefineFilterChain(){
        List<String> defineFilterChain = new ArrayList<>();
        defineFilterChain.add("/swagger-resources/** = anon");
        defineFilterChain.add("/user = anon");
        defineFilterChain.add("/swagger-ui.html = anon");
        defineFilterChain.add("/webjars/** = anon");
        defineFilterChain.add("/v2/** = anon");
        defineFilterChain.add("/** = upcAuth");
        return defineFilterChain;
    }


}
