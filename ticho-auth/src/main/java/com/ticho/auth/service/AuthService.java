package com.ticho.auth.service;

import com.ticho.auth.dto.LoginDto;
import com.ticho.auth.util.JwtTemplate;
import com.ticho.auth.util.OAuth2AccessToken;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.util.Assert;
import com.ticho.common.security.constant.SecurityConst;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.service.LoadUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 17:31
 */
@Service
public class AuthService {

    @Autowired
    private LoadUserContext loadUserContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private HttpServletRequest httpServletRequest;

    private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();


    public OAuth2AccessToken login(LoginDto loginDto) throws AuthenticationException {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        Assert.isNotBlank(username, BizErrCode.PARAM_ERROR);
        Assert.isNotBlank(password, BizErrCode.PARAM_ERROR);

        SecurityUser userDetails = loadUserContext.loadUser(username, SecurityConst.LOAD_USER_TYPE_USERNAME);
        userDetailsChecker.check(userDetails);
        Assert.isNotNull(userDetails, BizErrCode.PARAM_ERROR);
        String passwordAes = userDetails.getPassword();
        Assert.isTrue(passwordEncoder.matches(password, passwordAes), BizErrCode.FAIL, "密码输入不正确");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "N/A", userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long lat = System.currentTimeMillis();
        Long exp = System.currentTimeMillis() + Duration.ofHours(1).toMillis();
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken();
        oAuth2AccessToken.setRefreshToken("");
        oAuth2AccessToken.setIat(lat);
        oAuth2AccessToken.setExp(exp);
        oAuth2AccessToken.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("lat", lat);
        tokenInfo.put("exp", exp);
        tokenInfo.put("expiresIn", exp);
        tokenInfo.put("username", username);
        tokenInfo.put("authorities", authorities);
        tokenInfo.put("status", userDetails.getStatus());
        String accessToken = JwtTemplate.encode(tokenInfo);
        oAuth2AccessToken.setAccessToken(accessToken);
        return oAuth2AccessToken;
    }

}
