package com.xcjy.auth;


import com.xcjy.auth.filter.UpcAuthFilter;
import com.xcjy.auth.filter.UpcLoginFilter;
import com.xcjy.auth.manager.UpcSecurityManager;
import com.xcjy.auth.service.AuthMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcFilterFactoryBean extends ShiroFilterFactoryBean {

    public UpcFilterFactoryBean(UpcSecurityManager upcSecurityManager, AuthMessageService authMessageService, List<String> defineFilterChain) {
        setSecurityManager(upcSecurityManager);

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("upcAuth", new UpcAuthFilter(authMessageService));
        filterMap.put("upcLogin", new UpcLoginFilter(authMessageService));
        setFilters(filterMap);
        Map<String, String> definitionMap = new LinkedHashMap<>();
        definitionMap.put("/auth/login", "upcLogin");
        if (CollectionUtils.isNotEmpty(defineFilterChain)) {
            defineFilterChain.stream().forEach(chain -> {
                String[] uriList = chain.split("=");
                definitionMap.put(uriList[0].trim(), uriList[1].trim());
            });
        }
        setFilterChainDefinitionMap(definitionMap);
    }
}
