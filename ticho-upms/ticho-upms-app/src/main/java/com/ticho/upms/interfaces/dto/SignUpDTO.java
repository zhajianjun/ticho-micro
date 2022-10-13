package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 13:31
 */
@Data
@ApiModel(value = "注册DTO")
public class SignUpDTO {

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

}
