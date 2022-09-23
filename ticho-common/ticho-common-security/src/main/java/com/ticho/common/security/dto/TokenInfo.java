package com.ticho.common.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-09-23 08:56
 */
@Data
public class TokenInfo {

    @JsonProperty("username")
    private String username;

    @JsonProperty("lat")
    private Integer lat;

    @JsonProperty("exp")
    private Integer exp;

    @JsonProperty("expiresIn")
    private Integer expiresIn;

    @JsonProperty("authorities")
    private List<?> authorities;

    @JsonProperty("status")
    private Integer status;


}
