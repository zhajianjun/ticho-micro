package com.ticho.common.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ticho.common.security.constant.OAuth2Const;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-21 16:52
 */
@Data
public class OAuth2AccessToken {

    /** token */
    @ApiModelProperty(value = "token", position = 10)
    @JsonProperty(OAuth2Const.ACCESS_TOKEN)
    public String accessToken;

    /** refresh token  */
    @ApiModelProperty(value = "refresh token", position = 20)
    @JsonProperty(OAuth2Const.REFRESH_TOKEN)
    public String refreshToken;

    /** 开始时间戳，单位(ms) */
    @ApiModelProperty(value = "开始时间戳，单位(ms)", position = 30)
    @JsonProperty(OAuth2Const.IAT)
    public Long iat;

    /** 剩余时间，单位(ms)  */
    @ApiModelProperty(value = "剩余时间，单位(ms)", position = 40)
    @JsonProperty(OAuth2Const.EXPIRES_IN)
    public Long expiresIn;

    /** 结束时间时间戳，单位(ms)  */
    @ApiModelProperty(value = "结束时间时间戳，单位(ms)", position = 50)
    @JsonProperty(OAuth2Const.EXP)
    public Long exp;

    @ApiModelProperty(value = "自定义扩展信息", position = 60)
    @JsonIgnore
    public Map<String, Object> extInfo;


    @JsonIgnore
    public boolean isExpired() {
        return exp < System.currentTimeMillis();
    }

    public Long getExpiresIn() {
        return exp - System.currentTimeMillis();
    }
}
