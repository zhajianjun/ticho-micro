package com.ticho.uaa.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ticho.boot.web.util.JsonUtil;
import com.ticho.uaa.entity.OauthClientDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    //@Autowired
    //private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Value("${ticho.uaa.redirectUri}")
    private String url;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // 需要做缓存或者线程变量
        //// OauthClientDetails oauthClientDetails = oauthClientDetailsMapper.selectById(clientId);
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId("web");
        // web
        oauthClientDetails.setClientSecret("$2a$10$4A9kkAjXqFzyRqFhkxDqwOvV7qBJNtvkVh1XwEVodqNpx82/F8eQm");
        oauthClientDetails.setResourceIds("resource,resource1");
        oauthClientDetails.setScope("all,read,write");
        oauthClientDetails.setAuthorizedGrantTypes("password,authorization_code,implicit,client_credentials,refresh_token");
        oauthClientDetails.setWebServerRedirectUri(url);
        oauthClientDetails.setAuthorities(null);
        oauthClientDetails.setAccessTokenValidity(1800);
        oauthClientDetails.setRefreshTokenValidity(3600);
        oauthClientDetails.setAdditionalInformation(null);
        oauthClientDetails.setAutoapprove("true");
        //oauthClientDetails.setRemark();
        //oauthClientDetails.setCreateBy();
        //oauthClientDetails.setCreateTime();
        //oauthClientDetails.setUpdateBy();
        //oauthClientDetails.setUpdateTime();
        //oauthClientDetails.setIsDeleted();

        //if (oauthClientDetails == null) {
        //    throw new NoSuchClientException("this client is not exists");
        //}
        //if (oauthClientDetails.getIsDeleted()) {
        //    throw new NoSuchClientException("this client is delete");
        //}
        return getBaseClient(oauthClientDetails);
    }

    public void addClientDetails(OauthClientDetails clientDetails) throws ClientAlreadyExistsException {
    }

    public void updateClientDetails(OauthClientDetails clientDetails) throws NoSuchClientException {

    }

    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

    }

    public void removeClientDetails(String clientId) throws NoSuchClientException {

    }

    public List<ClientDetails> listClientDetails() {
        return null;
    }


    private BaseClientDetails getBaseClient(OauthClientDetails oauthClientDetails) {
        // @formatter:off
        if (oauthClientDetails == null) {
            return null;
        }
        String resourceIds = oauthClientDetails.getResourceIds();
        String authorities = oauthClientDetails.getAuthorities();
        String authorizedGrantTypes = oauthClientDetails.getAuthorizedGrantTypes();
        String scope = oauthClientDetails.getScope();
        String autoapprove = oauthClientDetails.getAutoapprove();

        BaseClientDetails baseClientDetails = new BaseClientDetails();
        baseClientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        baseClientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
        baseClientDetails.setAuthorities(getSimpleGrantedAuthorities(authorities));
        baseClientDetails.setAuthorizedGrantTypes(split(authorizedGrantTypes));
        baseClientDetails.setClientId(oauthClientDetails.getClientId());
        baseClientDetails.setClientSecret(oauthClientDetails.getClientSecret());
        baseClientDetails.setRegisteredRedirectUri(splitToSet(oauthClientDetails.getWebServerRedirectUri()));
        baseClientDetails.setScope(split(scope));
        baseClientDetails.setResourceIds(split(resourceIds));
        baseClientDetails.setAutoApproveScopes(split(autoapprove));
        baseClientDetails.setAdditionalInformation(JsonUtil.toMap(oauthClientDetails.getAdditionalInformation(), String.class, Object.class));
        // @formatter:on
        return baseClientDetails;
    }

    private OauthClientDetails getOauthClientDetails(BaseClientDetails baseClientDetails) {
        // @formatter:off
        if (baseClientDetails == null) {
            return null;
        }
        Set<String> resourceIds = baseClientDetails.getResourceIds();
        Collection<GrantedAuthority> authorities = baseClientDetails.getAuthorities();
        Set<String> authorizedGrantTypes = baseClientDetails.getAuthorizedGrantTypes();
        Set<String> scope = baseClientDetails.getScope();
        Set<String> autoApproveScopes = baseClientDetails.getAutoApproveScopes();

        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setAccessTokenValidity(baseClientDetails.getAccessTokenValiditySeconds());
        oauthClientDetails.setRefreshTokenValidity(baseClientDetails.getRefreshTokenValiditySeconds());
        oauthClientDetails.setAuthorities(getAuthoritieStr(authorities));
        oauthClientDetails.setAuthorizedGrantTypes(join(authorizedGrantTypes));
        oauthClientDetails.setClientId(baseClientDetails.getClientId());
        oauthClientDetails.setClientSecret(baseClientDetails.getClientSecret());
        oauthClientDetails.setWebServerRedirectUri(join(baseClientDetails.getRegisteredRedirectUri()));
        oauthClientDetails.setScope(join(scope));
        oauthClientDetails.setAutoapprove(join(autoApproveScopes));
        oauthClientDetails.setResourceIds(join(resourceIds));
        oauthClientDetails.setAdditionalInformation(JsonUtil.toJsonString(baseClientDetails.getAdditionalInformation()));
        // @formatter:on
        return oauthClientDetails;
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

    private String join(Collection<String> strs) {
        // @formatter:off
        if (CollectionUtil.isEmpty(strs)){
            return null;
        }
        return String.join("," ,strs);
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

    private String getAuthoritieStr(Collection<GrantedAuthority> simpleGrantedAuthorities) {
        // @formatter:off
        if (CollectionUtil.isEmpty(simpleGrantedAuthorities)){
            return null;
        }
        return simpleGrantedAuthorities
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        // @formatter:on
    }
}
