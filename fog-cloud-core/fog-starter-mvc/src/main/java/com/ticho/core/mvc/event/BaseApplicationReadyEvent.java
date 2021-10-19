package com.ticho.core.mvc.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @see ApplicationReadyEvent 应用程序已准备好执行的事件
 * @author AdoroTutto
 * @date 2021-10-17 10:35
 */
@Slf4j
@Component
public class BaseApplicationReadyEvent implements ApplicationListener<ApplicationReadyEvent> {


    /**
     * 默认事件
     *
     * @param event ApplicationReadyEvent
     */
    @Override
    @Async
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Environment env = applicationContext.getEnvironment();
        log.info("application is ready");
    }
}
