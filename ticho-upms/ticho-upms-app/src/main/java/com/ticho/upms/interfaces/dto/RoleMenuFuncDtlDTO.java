package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色菜单功能号详情
 *
 * @author zhajianjun
 * @date 2023-01-30 13:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户角色菜单功能号详情")
public class RoleMenuFuncDtlDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色id列表 */
    @ApiModelProperty(value = "角色id列表", position = 220)
    private List<Long> roleIds;

    /** 角色code列表 */
    @ApiModelProperty(value = "角色code列表", position = 230)
    private List<String> roleCodes;

    /** 菜单id列表 */
    @ApiModelProperty(value = "菜单id列表", position = 240)
    private List<Long> menuIds;

    /** 菜单功能号信息 */
    @ApiModelProperty(value = "菜单功能号信息", position = 250)
    private List<RoleDTO> roles;

    /** 菜单信息 */
    @ApiModelProperty(value = "菜单信息", position = 260)
    private List<MenuFuncDtlDTO> menus;

}
