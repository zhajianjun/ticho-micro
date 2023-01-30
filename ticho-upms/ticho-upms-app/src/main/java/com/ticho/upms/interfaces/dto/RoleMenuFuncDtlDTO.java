package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单功能号详情
 *
 * @author zhajianjun
 * @date 2023-01-30 13:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色菜单功能号详情")
public class RoleMenuFuncDtlDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 10)
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

    /** 菜单id列表 */
    @ApiModelProperty(value = "菜单id列表", position = 30)
    private List<Long> menuIds;

    /** 菜单功能号信息 */
    @ApiModelProperty(value = "菜单功能号信息", position = 40)
    private List<MenuFuncDtlDTO> menuFuncs;

}
