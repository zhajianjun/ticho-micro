package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色信息DTO
 *
 * @author zhajianjun
 * @date 2022-10-10 17:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色信息DTO")
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号;", position = 10)
    private Long id;

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码", position = 20)
    private String code;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称", position = 30)
    private String name;

    /** 租户编号 */
    @ApiModelProperty(value = "租户编号", position = 40)
    private String tenantId;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 50)
    private String remark;

}
