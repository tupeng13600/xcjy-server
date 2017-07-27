package com.xcjy.auth.realm;

import com.xcjy.auth.matcher.UpcCredentialsMatcher;
import com.xcjy.auth.model.UpcUser;
import com.xcjy.auth.service.AuthMessageService;
import com.xcjy.auth.token.UpcToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/16.
 */
public class UpcRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpcRealm.class);

    private AuthMessageService authMessageService;

    public UpcRealm(AuthMessageService authMessageService) {
        super.setCredentialsMatcher(new UpcCredentialsMatcher());
        super.setCachingEnabled(false); //关闭缓存
        super.setAuthenticationCachingEnabled(false); //关闭认证换缓存
        if (null == authMessageService) {
            LOGGER.warn("the authMessageService is null.");
        }
        this.authMessageService = authMessageService;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String principal = (String) principals.getPrimaryPrincipal();
        if (StringUtils.isEmpty(principal)) {
            LOGGER.warn("can not get principal for this user");
            return new SimpleAuthorizationInfo();
        }
        Set<String> roles = authMessageService.getRole(principal);
        return new SimpleAuthorizationInfo(roles);
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UpcUser user = authMessageService.getUser(((UpcToken) token).getUsername());
        if (null == user) {
            LOGGER.error("登录失败，用户不存在!!");
            throw new UnknownAccountException("用户不存在");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), new SimpleByteSource(user.getSalt()), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UpcToken;
    }

    public void setAuthMessageService(AuthMessageService authMessageService) {
        this.authMessageService = authMessageService;
    }


}
