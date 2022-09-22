package com.ticho.common.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ticho.common.security.constant.OAuth2Const;
import lombok.Data;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 16:52
 */
@Data
public class OAuth2AccessToken {

    /** token */
    @JsonProperty(OAuth2Const.ACCESS_TOKEN)
    public String accessToken;

    /** refresh token  */
    @JsonProperty(OAuth2Const.REFRESH_TOKEN)
    public String refreshToken;

    /** 开始时间戳 */
    @JsonProperty(OAuth2Const.IAT)
    public Long iat;

    /** 剩余时间 */
    @JsonProperty(OAuth2Const.EXPIRES_IN)
    public Long expiresIn;

    /** 结束时间时间戳 */
    @JsonProperty(OAuth2Const.EXP)
    public Long exp;


    @JsonIgnore
    public boolean isExpired() {
        return exp < System.currentTimeMillis();
    }

    public Long getExpiresIn() {
        return exp - System.currentTimeMillis();
    }
}
