package com.xcjy.auth.cache;

import com.xcjy.auth.token.UpcToken;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tupeng on 2017/8/8.
 */
public abstract class AuthCache {

    private static final Long expire = 30 * 60 * 1000L;

    private static final Map<String, UpcToken> tokenResource = new ConcurrentHashMap<>();

    private static final Map<String, Long> tokenTime = new ConcurrentHashMap<>();

    public static UpcToken get(String token) {
        if (expire(token)) {
            remove(token);
        }
        tokenTime.put(token, System.currentTimeMillis());
        return tokenResource.get(token);
    }

    public static void put(String key, UpcToken token) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        tokenResource.put(key, token);
        tokenTime.put(key, System.currentTimeMillis());
    }

    private static Boolean expire(String token) {
        return null == tokenTime.get(token) || System.currentTimeMillis() > (tokenTime.get(token) + expire);
    }

    public static void remove(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        tokenResource.remove(token);
        tokenTime.remove(token);
    }

}
