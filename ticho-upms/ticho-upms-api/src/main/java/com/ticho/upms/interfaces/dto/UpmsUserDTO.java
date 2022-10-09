package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 * @author zhajianjun
 * @date 2020-07-02 20:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "权限用户信息")
public class UpmsUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户", position = 10)
    private String username;

    @ApiModelProperty(value = "密码", position = 20)
    private String password;

    @ApiModelProperty(value = "用户状态;1-正常,2-已失效,3-已被锁定,4-已过期", position = 30)
    private Integer status;

    @ApiModelProperty(value = "角色", position = 40)
    private List<String> roleIds;

    @ApiModelProperty(value = "部门", position = 50)
    private List<String> deptIds;

}
