package com.ticho.common.security.service;

import cn.hutool.core.collection.CollUtil;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.exception.BizException;
import com.ticho.boot.view.util.Assert;
import com.ticho.common.security.constant.OAuth2Const;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.OAuth2AccessToken;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.convert.JwtConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-22 18:12
 */
@Component
public class LoginUserContext {

    @Autowired
    private Map<String, LoginUserService> loginUserServiceMap;

    @Autowired
    private LoadUserService loadUserService;

    @Autowired
    private JwtConverter jwtConverter;

    @Autowired
    private Map<String, JwtExtInfo> jwtExtInfoMap;

    public OAuth2AccessToken token(String account, String credentials, String type) {
        // @formatter:off
        LoginUserService loadUserService = loginUserServiceMap.get(SecurityConst.LOGIN_USER_TYPE + type);
        if (loadUserService == null) {
            loadUserService = loginUserServiceMap.get(SecurityConst.LOGIN_USER_TYPE_USERNAME);
        }
        SecurityUser securityUser = loadUserService.login(account, credentials);
        return getoAuth2AccessToken(securityUser);
        // @formatter:on
    }

    public OAuth2AccessToken refreshToken(String refreshToken) {
        // @formatter:off
        Map<String, Object> decodeAndVerify = jwtConverter.decodeAndVerify(refreshToken);
        Object type = decodeAndVerify.getOrDefault(OAuth2Const.TYPE, "");
        Assert.isTrue(Objects.equals(type, OAuth2Const.REFRESH_TOKEN), BizErrCode.FAIL, "refreshToken不合法");
        String username = Optional.ofNullable(decodeAndVerify.get(OAuth2Const.USERNAME))
            .map(Object::toString)
            .orElseThrow(()-> new BizException(BizErrCode.FAIL, "用户名不存在"));
        SecurityUser securityUser = loadUserService.loadUser(username);
        return getoAuth2AccessToken(securityUser);
        // @formatter:on
    }

    private OAuth2AccessToken getoAuth2AccessToken(SecurityUser securityUser) {
        UsernamePasswordAuthenticationToken authentication = getUsernamePasswordAuthenticationToken(securityUser);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken();
        Map<String,Object> map = new HashMap<>(8);
        for (JwtExtInfo value : jwtExtInfoMap.values()) {
            Map<String,Object> ext = value.getExt();
            if (CollUtil.isEmpty(ext)) {
                continue;
            }
            map.putAll(ext);
        }
        oAuth2AccessToken.setExtInfo(map);
        jwtConverter.encode(oAuth2AccessToken, securityUser);
        return oAuth2AccessToken;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(SecurityUser securityUser) {
        return new UsernamePasswordAuthenticationToken(securityUser, "N/A", securityUser.getAuthorities());
    }



}
