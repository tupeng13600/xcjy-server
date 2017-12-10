package com.xcjy.web.common.schedule;

import com.xcjy.auth.cache.AuthCache;
import com.xcjy.web.common.cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by tupeng on 2017/8/25.
 */
@Component
public class Schedule {

    private static Logger logger = LoggerFactory.getLogger(Schedule.class);

    @Autowired
    private CacheFactory cacheFactory;

    @Scheduled(cron = "0 */5 * * * ?")
    public void updateAuthCache(){
        AuthCache.update();
    }

    @Scheduled(cron = "0 */2 * * * ?")
    public void updateBaseCache(){
        cacheFactory.init();
    }

}
