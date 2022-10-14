package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 *
 * @author zhajianjun
 * @date 2022-10-14 10:56
 */
@Data
@ApiModel(value = "租户注册DTO")
public class TenantSignUpDTO {

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 10)
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称", position = 20)
    @NotBlank(message = "租户名称不能为空")
    private String tenantName;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 30)
    private String remark;

}
