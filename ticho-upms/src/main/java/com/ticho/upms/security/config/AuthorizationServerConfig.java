package com.ticho.upms.security.config;


import com.ticho.upms.security.exception.Oauth2ExceptionTranslator;
import com.ticho.upms.security.filter.CustomClientCredentialsTokenEndpointFilter;
import com.ticho.upms.security.interceptor.LoginInterceptor;
import com.ticho.upms.security.view.ClientExceptionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

/**
 * 授权服务器
 *
 * @author zhajianjun
 * @date 2020-07-02 12:36
 */
@Configuration
@EnableAuthorizationServer
@Order(0)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("customTokenExtraInfo")
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));
        // @formatter:off
        endpoints.authenticationManager(authenticationManager)
            // 允许获取token的请求方法
            //.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
            // 用户查询服务
            .userDetailsService(userDetailsService)
            // token存储方式
            .tokenStore(tokenStore)
            // jwt来进行编码以及解码的类
            .accessTokenConverter(jwtAccessTokenConverter)
            // 向token中添加自定义信息
            .tokenEnhancer(tokenEnhancerChain)
            // 异常转换
            .exceptionTranslator(new Oauth2ExceptionTranslator())
            // 获取token接口修改
            .pathMapping("/oauth/token", "/oauth/token")
            // 配置自定义code
            .authorizationCodeServices(authorizationCodeServices)
            // 登录拦截
            .addInterceptor(new LoginInterceptor());
    }

    /**
     * 授权服务器安全配置器
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // client错误返回视图修改
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        ClientExceptionView clientExceptionView = new ClientExceptionView();
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(clientExceptionView);

        security
            // /oauth/check_token（检查token）放行
            .checkTokenAccess("permitAll()")
            // /oauth/token_key（jwt密钥，只有JwtAccessTokenConverter注入容器才会有这个接口）保护
            .tokenKeyAccess("permitAll()")
            .authenticationEntryPoint(clientExceptionView)
            .addTokenEndpointAuthenticationFilter(endpointFilter);
    }
}
