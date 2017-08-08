package com.xcjy.auth.cache;

import com.xcjy.auth.model.UpcUser;
import com.xcjy.auth.token.UpcToken;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tupeng on 2017/8/8.
 */
public abstract class AuthCache {

    private static final Long expire = 30 * 60 * 1000L;

    private static final Map<String, UpcUser> userResource = new ConcurrentHashMap<>();

    private static final Map<String, UpcToken> tokenResource = new ConcurrentHashMap<>();

    private static final Map<String, Long> tokenTime = new ConcurrentHashMap<>();

    public static UpcUser getUser(String token) {
        if(expire(token)) {
            remove(token);
        }
        tokenTime.put(token, System.currentTimeMillis());
        return userResource.get(token);
    }

    public static void put(String key, UpcUser user, UpcToken token) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        userResource.put(key, user);
        tokenResource.put(key, token);
        tokenTime.put(key, System.currentTimeMillis());
    }

    public static UpcToken getToken(String token) {
        if(expire(token)) {
            remove(token);
        }
        tokenTime.put(token, System.currentTimeMillis());
        return tokenResource.get(token);
    }

    private static Boolean expire(String token){
        return null == tokenTime.get(token) || System.currentTimeMillis() > (tokenTime.get(token) + expire);
    }

    public static void remove(String token) {
        if(StringUtils.isBlank(token)) {
            return;
        }
        userResource.remove(token);
        tokenResource.remove(token);
        tokenTime.remove(token);
    }

}
