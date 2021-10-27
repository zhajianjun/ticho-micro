package com.ticho.uaa.security.config;



import com.ticho.uaa.security.exception.Oauth2ExceptionTranslator;
import com.ticho.uaa.security.filter.CustomClientCredentialsTokenEndpointFilter;
import com.ticho.uaa.security.interceptor.LoginInterceptor;
import com.ticho.uaa.security.view.ClientExceptionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 授权服务器
 *
 * @author AdoroTutto
 * @date 2020-07-02 12:36
 */
@Configuration
@EnableAuthorizationServer
@Order(0)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Oauth2ExceptionTranslator oauth2ExceptionTranslator;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    @Qualifier("customTokenExtraInfo")
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()//Token保存在内存中
                //指明client-id和client-secret
                .withClient("web")
                .secret(passwordEncoder.encode("web"))
                .resourceIds("resource")
                // 令牌有效时间，单位秒
                .accessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30))
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1))
                // 支持 密码模式、授权码模式、简化模式、客户端模式和刷新令牌
                .authorizedGrantTypes("password", "authorization_code", "implicit", "client_credentials", "refresh_token")
                // 权限有哪些,如果这两配置了该参数，客户端发请求可以不带参数，使用配置的参数
                .scopes("all", "read", "write")
                .autoApprove(true)
                .redirectUris("http://www.baidu.com", "http://localhost:8081/webjars/oauth/oauth2.html", "http://localhost:8080/authorizationCode");
        // @formatter:on
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
                .exceptionTranslator(oauth2ExceptionTranslator)
                // 获取token接口修改
                .pathMapping("/oauth/token", "/oauth/token")
                // 配置自定义code
                .authorizationCodeServices(authorizationCodeServices)
                // 登录拦截
                .addInterceptor(loginInterceptor);
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
                .tokenKeyAccess("isAuthenticated()")
                .authenticationEntryPoint(clientExceptionView)
                .addTokenEndpointAuthenticationFilter(endpointFilter);
    }
}
