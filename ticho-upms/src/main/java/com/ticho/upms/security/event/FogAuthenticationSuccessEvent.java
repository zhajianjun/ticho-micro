package com.ticho.upms.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 登录和token认证成功回调的方法
 *
 * @author zhajianjun
 * @date 2020-06-14 13:55
 */
@Component
public class FogAuthenticationSuccessEvent implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger log = LoggerFactory.getLogger(FogAuthenticationSuccessEvent.class);

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // 这里的事件源除了登录事件（UsernamePasswordAuthenticationToken）还有可能是token验证事件源（OAuth2Authentication）
        if (!UsernamePasswordAuthenticationToken.class.equals(event.getSource().getClass())) {
            log.debug("Token校验成功");
            return;
        }
        if (event.getAuthentication().getPrincipal() instanceof UserDetails) {
            log.debug("登录成功");
        }
    }
}