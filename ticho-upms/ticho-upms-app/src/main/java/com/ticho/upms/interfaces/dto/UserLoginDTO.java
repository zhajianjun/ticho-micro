package com.ticho.upms.interfaces.dto;

import com.ticho.boot.security.dto.LoginRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录DTO
 *
 * @author zhajianjun
 * @date 2022-10-14 10:30
 */
@Data
@ApiModel(value = "登录DTO")
public class UserLoginDTO implements LoginRequest {

    /** 租户编号 */
    @NotBlank(message = "租户编号不能为空")
    @ApiModelProperty(value = "租户编号", position = 10)
    private String tenantId;

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", position = 20)
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", position = 30)
    private String password;

    /** 登录类型 */
    @ApiModelProperty(value = "登录类型", position = 40)
    private String type;

}
