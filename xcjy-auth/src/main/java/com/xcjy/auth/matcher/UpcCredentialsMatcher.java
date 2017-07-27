package com.xcjy.auth.matcher;

import com.xcjy.auth.util.UpcSecurityUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * Created by tupeng on 2017/7/17.
 */
public class UpcCredentialsMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        SimpleAuthenticationInfo simpleInfo = (SimpleAuthenticationInfo) info;
        String desPwd = UpcSecurityUtil.encryptPwd((String) token.getCredentials(), simpleInfo.getCredentialsSalt());
        if (desPwd.equals(info.getCredentials())) {
            return true;
        }
        return false;
    }
}
