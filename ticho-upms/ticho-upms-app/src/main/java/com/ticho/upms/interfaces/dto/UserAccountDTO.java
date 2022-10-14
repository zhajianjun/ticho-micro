package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户登录账号信息
 *
 * @author zhajianjun
 * @date 2022-10-14 13:17
 */
@Data
@ApiModel(value = "用户登录账号信息")
public class UserAccountDTO {

    @ApiModelProperty(value = "租户ID", position = 10)
    private String tenantId;

    @ApiModelProperty(value = "登录账户", position = 20)
    private String username;

    @ApiModelProperty(value = "邮箱", position = 30)
    private String email;

    @ApiModelProperty(value = "手机号码", position = 40)
    private String mobile;

    @ApiModelProperty(value = "用户状态", position = 50)
    private List<Integer> status;

}
