package com.ticho.uaa.security.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import com.ticho.boot.web.util.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义token额外的信息
 *
 * @author zhajianjun
 * @date 2020-07-03 15:39
 */
public class CustomTokenExtraInfo implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication userAuthentication = authentication.getUserAuthentication();
        Object principal = userAuthentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>(16);
        additionalInfo.put("author", "zhajianjun");
        additionalInfo.put("createTime", DatePattern.NORM_DATETIME_FORMATTER.format(LocalDateTime.now()));
        additionalInfo.put("user", JsonUtil.toMap(principal));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    public static void main(String[] args) {
        String key = "eyJhdWQiOlsicmVzb3VyY2UiLCJyZXNvdXJjZTEiXSwiY3JlYXRlVGltZSI6IjIwMjItMDktMDcgMTM6NTg6MTAiLCJ1c2VyX25hbWUiOiJhZG1pbiIsImF1dGhvciI6InpoYWppYW5qdW4iLCJzY29wZSI6WyJhbGwiLCJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjYyNTMyMDg5LCJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluIiwibmlja25hbWUiOiLmnIjkuIropb_msrMiLCJyZWFsbmFtZSI6IuW8oOS4iSIsImlkY2FyZCI6IjM0MDgyMjE5OTYwMTAxNDcxMCIsInNleCI6MCwiYWdlIjozNCwicXEiOjEwMTkzMTk0NzMsIm1vYmlsZSI6IjEzOTY2OTgxNjExIiwicGhvdG8iOiJhZG1pbi8xNDcxMzQwNzc3NTQ3MjM1MzI4LmpwZyIsInN0YXR1cyI6MSwiY3JlYXRlQnkiOiJhZG1pbiIsImNyZWF0ZVRpbWUiOiIyMDIxLTExLTAyIDE1OjMyOjQwIiwidXBkYXRlQnkiOiJhZG1pbiIsInVwZGF0ZVRpbWUiOiIyMDIyLTAyLTE3IDE2OjQ1OjM4IiwiaXNEZWxldGVkIjowLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiYWRtaW4ifV0sImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJhY2NvdW50Tm9uTG9ja2VkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWUsImVuYWJsZWQiOnRydWV9LCJhdXRob3JpdGllcyI6WyJhZG1pbiJdLCJqdGkiOiJlYjllYzQwNi1kYzM2LTRmYzAtYWZlNy1lMzczOTZhMjFjZjEiLCJjbGllbnRfaWQiOiJ3ZWIifQ";
        System.out.println(Base64.decodeStr(key));
    }

}
