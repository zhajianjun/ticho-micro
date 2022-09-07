package com.ticho.uaa.security.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ticho.uaa.entity.OauthCode;
import com.ticho.uaa.service.OauthCodeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Service;

/**
 * 授权码模式，code自定义
 *
 * @author zhajianjun
 * @date 2021-10-31 0:30
 */
@Service
@Primary
@Slf4j
public class AuthorizationCodeServicesImpl implements AuthorizationCodeServices {

    @Autowired
    private OauthCodeService oauthCodeService;


    @SneakyThrows
    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        log.warn("create authorization code");
        String code = IdUtil.fastSimpleUUID();
        byte[] serialize = SerializationUtils.serialize(authentication);
        // 必须是ISO-8859-1
        OauthCode oauthCode = new OauthCode();
        oauthCode.setCode(code);
        oauthCode.setAuthentication(serialize);
        oauthCodeService.save(oauthCode);
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        log.warn("consume authorization code");
        OauthCode oauthCode = oauthCodeService.getByCode(code);
        oauthCodeService.removeById(code);
        byte[] bytes = oauthCode.getAuthentication();
        return SerializationUtils.deserialize(bytes);
    }
}
