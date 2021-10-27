package com.ticho.uaa.security.config;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 授权码模式，code自定义
 *
 * @author AdoroTutto
 * @date 2021-09-11 23:21
 */
@Component
@Slf4j
public class CustomAuthorizationCodeServices implements AuthorizationCodeServices {
    private final ConcurrentMap<String, OAuth2Authentication> codes = new ConcurrentHashMap<>();

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        log.warn("create authorization code");
        String code = IdUtil.fastSimpleUUID();
        codes.put(code, authentication);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        log.warn("consume authorization code");
        return codes.remove(code);
    }
}
