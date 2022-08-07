package com.ticho.uaa.security.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.stereotype.Component;

/**
 * 未传入token(未登录)，权限不足监听事件
 *
 * @author zhajianjun
 * @date 2020-06-14 14:59
 */
@Slf4j
@Component
public class FogAuthorizationFailureEvent implements ApplicationListener<AuthorizationFailureEvent> {

    @Override
    public void onApplicationEvent(@NonNull AuthorizationFailureEvent event) {
        log.debug("请登录");
    }

}
