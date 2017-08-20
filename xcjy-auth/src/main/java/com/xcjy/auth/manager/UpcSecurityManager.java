package com.xcjy.auth.manager;


import com.xcjy.auth.realm.UpcRealm;
import com.xcjy.auth.service.AuthMessageService;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcSecurityManager extends DefaultWebSecurityManager {
    public UpcSecurityManager(AuthMessageService authMessageService) {
        super(new UpcRealm(authMessageService));
    }
}
