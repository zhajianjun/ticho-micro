package com.ticho.common.security.component;

import com.ticho.boot.web.event.BaseApplicationReadyEvent;
import com.ticho.common.security.handle.CacheHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;

/**
 * 程序启动成功把当前应用的权限标识缓存到redis中
 *
 * @see ApplicationReadyEvent 应用程序已准备好执行的事件
 *
 * @author zhajianjun
 * @date 2022-07-10 15:56:30
 */
@Slf4j
public class PermRedisCacheEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final CacheHandle cacheHandle;

    public PermRedisCacheEvent(CacheHandle cacheHandle) {
        this.cacheHandle = cacheHandle;
    }

    /**
     * 默认事件
     *
     * @param event ApplicationReadyEvent
     */
    @Override
    @Async
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        cacheHandle.pushCurrentAppPerms();
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Environment env = applicationContext.getEnvironment();
        String property = env.getProperty(BaseApplicationReadyEvent.SPRING_APPLICATION_NAME_KEY, "application");
        log.info("{} perms is cached", property);
    }
}
