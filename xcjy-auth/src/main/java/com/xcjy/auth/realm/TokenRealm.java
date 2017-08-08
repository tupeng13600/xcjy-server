package com.xcjy.auth.realm;

import com.xcjy.auth.cache.AuthCache;
import com.xcjy.auth.cache.TokenThreadLocal;
import com.xcjy.auth.model.UpcUser;
import com.xcjy.auth.service.AuthMessageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tupeng on 2017/8/8.
 */
public class TokenRealm extends UpcRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpcRealm.class);


    public TokenRealm(AuthMessageService authMessageService) {
        super(authMessageService);
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
        String accessToken = TokenThreadLocal.get();
        LOGGER.info("TokenRealm 获取到的TOKEN:{}", accessToken);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }
        UpcUser user = AuthCache.getUser(accessToken);
        if (null == user) {
            return null;
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), new SimpleByteSource(user.getSalt()), getName());
    }

}
