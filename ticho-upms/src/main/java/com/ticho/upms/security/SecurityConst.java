package com.ticho.upms.security;

import cn.hutool.core.codec.Base64;
import com.ticho.boot.web.util.JsonUtil;

/**
 *
 * @author zhajianjun
 * @date 2020-07-05 0:36
 */
public class SecurityConst {

    /**
     * 登录类型
     */
    public static final String DEFAULT_TYPE = "Default";
    public static final String EMAIL_TYPE = "Email";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "bearer";
    public static final String PERMIT_ALL = "permitAll";


    public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2UiXSwiY3JlYXRlVGltZSI6IjIwMjItMDktMDcgMTg6MDA6MDQiLCJ1c2VyX25hbWUiOiJhZG1pbiIsImF1dGhvciI6InpoYWppYW5qdW4iLCJzY29wZSI6WyJhbGwiLCJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjYyNTQ2NjA0LCJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluIiwibmlja25hbWUiOiLmnIjkuIropb_msrMiLCJyZWFsbmFtZSI6IuW8oOS4iSIsImlkY2FyZCI6IjM0MDgyMjE5OTYwMTAxNDcxMCIsInNleCI6MCwiYWdlIjozNCwicXEiOjEwMTkzMTk0NzMsIm1vYmlsZSI6IjEzOTY2OTgxNjExIiwicGhvdG8iOiJhZG1pbi8xNDcxMzQwNzc3NTQ3MjM1MzI4LmpwZyIsInN0YXR1cyI6MSwiY3JlYXRlQnkiOiJhZG1pbiIsImNyZWF0ZVRpbWUiOiIyMDIxLTExLTAyIDE1OjMyOjQwIiwidXBkYXRlQnkiOiJhZG1pbiIsInVwZGF0ZVRpbWUiOiIyMDIyLTAyLTE3IDE2OjQ1OjM4IiwiaXNEZWxldGVkIjowLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiYWRtaW4ifV0sImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJhY2NvdW50Tm9uTG9ja2VkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWUsImVuYWJsZWQiOnRydWV9LCJhdXRob3JpdGllcyI6WyJhZG1pbiJdLCJqdGkiOiI1MjFjMzE2OS0xOWU3LTQ0MWItOGQ0MC00YmVjZjJlYzRhNjgiLCJjbGllbnRfaWQiOiJ3ZWIifQ.JUHgEa_SuqcaFCRBodSGq7rMm4qzGDJ6eaG6X-f5K9kNK-3pO498VeV65W4APAjAq7ljPFrAb_6MUaynRwtUIYZqrcHdnvLiPfdWO0KN5s9r-zVeSOvDkH-78kFoKuo9b7x4pwcaLIHJ-GlgXEN3Kx0Sh6HDMfqqVV_OGuG_-4E-PDysFfmLjvguutHhLci6BE5XVgcCnBg4cXhSBvp7u-xkuaipbLMwJ-f1wh7TcNVWq_Cq0R4dzJXKvlRiqN9nKMkIeYIfp86l0JFo3bAqefaS0CWVAJl1ThcT7ziIgrbSOJmCilOK-Ago9q2mhpmaRF00Nm0Ts788dfQOUoLkqA";
        String decodeStr = Base64.decodeStr(token);
        System.out.println(decodeStr);
        System.out.println(JsonUtil.toMap(decodeStr));
    }
}
