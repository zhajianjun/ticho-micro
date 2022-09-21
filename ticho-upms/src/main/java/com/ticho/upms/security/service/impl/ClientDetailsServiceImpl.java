package com.ticho.upms.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.upms.entity.OauthClient;
import com.ticho.upms.security.interceptor.LoginInterceptor;
import com.ticho.upms.security.service.LoginHandleContext;
import com.ticho.upms.service.OauthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author zhajianjun
 * @date 2021-10-30 22:18
 */
@Service
@Primary
@SuppressWarnings({"AlibabaRemoveCommentedCode"})
@RefreshScope
public class ClientDetailsServiceImpl implements ClientDetailsService {

    // @formatter:off

    @Autowired
    private OauthClientService oauthClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        if (StrUtil.isBlank(clientId)) {
            throw new NoSuchClientException("this clientId is blank");
        }
        // 需要做缓存或者线程变量
        OauthClient oauthClient = LoginHandleContext.oauthClientThreadLocal.get();
        if (oauthClient == null) {
            oauthClient = oauthClientService.getByClientId(clientId);
            LoginHandleContext.oauthClientThreadLocal.set(oauthClient);
        }
        if (oauthClient == null) {
            throw new NoSuchClientException("this client is not exists");
        }
        return getBaseClient(oauthClient);
    }

    private BaseClientDetails getBaseClient(OauthClient oauthClient) {
        // @formatter:off
        if (oauthClient == null) {
            return null;
        }
        String resourceIds = oauthClient.getResourceIds();
        String authorities = oauthClient.getAuthorities();
        String authorizedGrantTypes = oauthClient.getAuthorizedGrantTypes();
        String scope = oauthClient.getScope();
        String autoapprove = oauthClient.getAutoapprove();

        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
        baseClientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());
        baseClientDetails.setAuthorities(getSimpleGrantedAuthorities(authorities));
        baseClientDetails.setAuthorizedGrantTypes(split(authorizedGrantTypes));
        baseClientDetails.setClientId(oauthClient.getClientId());
        baseClientDetails.setClientSecret(oauthClient.getClientSecret());
        baseClientDetails.setRegisteredRedirectUri(splitToSet(oauthClient.getWebServerRedirectUri()));
        baseClientDetails.setScope(split(scope));
        baseClientDetails.setResourceIds(split(resourceIds));
        baseClientDetails.setAutoApproveScopes(split(autoapprove));
        baseClientDetails.setAdditionalInformation(JsonUtil.toMap(oauthClient.getAdditionalInformation(), String.class, Object.class));
        // @formatter:on
        return baseClientDetails;
    }

    private List<String> split(String str) {
        // @formatter:off
        if (StrUtil.isBlank(str)){
            return new ArrayList<>();
        }
        return Arrays
            .stream(str.split(","))
            .map(String::trim)
            .collect(Collectors.toList());
        // @formatter:on
    }

    private Set<String> splitToSet(String str) {
        // @formatter:off
        if (StrUtil.isBlank(str)){
            return new HashSet<>();
        }
        return Arrays
            .stream(str.split(","))
            .map(String::trim)
            .collect(Collectors.toSet());
        // @formatter:on
    }


    private List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(String authorities) {
        // @formatter:off
        if (StrUtil.isBlank(authorities)){
            return new ArrayList<>();
        }
        return Arrays
            .stream(authorities.split(","))
            .map(String::trim)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        // @formatter:on
    }

}
