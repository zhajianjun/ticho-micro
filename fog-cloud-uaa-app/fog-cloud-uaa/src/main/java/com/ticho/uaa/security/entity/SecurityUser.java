package com.ticho.uaa.security.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ticho.uaa.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * 登录用户信息
 * @author AdoroTutto
 * @date 2020-07-02 20:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录用户信息")
@ToString
public class SecurityUser extends User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户", position = 20)
    private String username;

    @ApiModelProperty(value = "密码", position = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(value = "用户状态。1-正常,2-已失效,3-已被锁定,4-已过期", position = 181)
    private Integer status;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != 4;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != 3;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != 2;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }
}
