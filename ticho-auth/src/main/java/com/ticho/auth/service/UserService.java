package com.ticho.auth.service;

import com.ticho.auth.util.JwtTemplate;
import com.ticho.auth.util.OAuth2AccessToken;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 17:31
 */
@Service
public class UserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public OAuth2AccessToken login(String username, String password) throws AuthenticationException {
        Assert.isNotBlank(username, BizErrCode.PARAM_ERROR);
        Assert.isNotBlank(password, BizErrCode.PARAM_ERROR);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Assert.isNotNull(userDetails, BizErrCode.PARAM_ERROR);
        String passwordAes = userDetails.getPassword();
        Assert.isTrue(passwordEncoder.matches(password, passwordAes), BizErrCode.FAIL, "密码输入不正确");
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long lat = System.currentTimeMillis();
        Long exp = System.currentTimeMillis() + Duration.ofHours(1).toMillis();

        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken();
        oAuth2AccessToken.setRefreshToken("");
        oAuth2AccessToken.setIat(lat);
        oAuth2AccessToken.setExp(exp);
        oAuth2AccessToken.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("lat", lat);
        tokenInfo.put("exp", exp);
        tokenInfo.put("expiresIn", exp);
        tokenInfo.put("username", username);
        String accessToken = JwtTemplate.encode(tokenInfo);
        oAuth2AccessToken.setAccessToken(accessToken);
        return oAuth2AccessToken;
    }

}
