package com.ticho.upms.interfaces.dto;

import com.ticho.upms.infrastructure.core.util.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单功能号详情信息
 *
 * @author zhajianjun
 * @date 2023-01-30 13:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "菜单功能号详情信息")
public class MenuFuncDtlDTO extends TreeNode<MenuFuncDtlDTO> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 菜单id */
    @ApiModelProperty(value = "菜单id", position = 10)
    private Long id;

    /** 父级id */
    @ApiModelProperty(value = "父级id", position = 20)
    private Long parentId;

    /** 类型;1-目录,2-菜单 */
    @ApiModelProperty(value = "类型;1-目录,2-菜单", position = 40)
    private Integer type;

    /** 标题;目录名称、菜单名称 */
    @ApiModelProperty(value = "标题;目录名称、菜单名称", position = 50)
    private String name;

    /** 路由地址 */
    @ApiModelProperty(value = "路由地址", position = 60)
    private String path;

    /** 组件名称 */
    @ApiModelProperty(value = "组件名称", position = 70)
    private String component;

    /** 转发地址 */
    @ApiModelProperty(value = "转发地址", position = 80)
    private String redirect;

    /** 是否外链菜单;1-是,0-否 */
    @ApiModelProperty(value = "是否外链菜单;1-是,0-否", position = 90)
    private Integer extFlag;

    /** 是否缓存;1-是,0-否 */
    @ApiModelProperty(value = "是否缓存;1-是,0-否", position = 100)
    private Integer cacheAble;

    /** 菜单和目录是否可见;1-是,0-否 */
    @ApiModelProperty(value = "菜单和目录是否可见;1-是,0-否", position = 110)
    private Integer invisible;

    /** 菜单是否可关闭;1-是,0-否 */
    @ApiModelProperty(value = "菜单是否可关闭;1-是,0-否", position = 120)
    private String closable;

    /** 图标 */
    @ApiModelProperty(value = "图标", position = 130)
    private String icon;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 140)
    private Integer sort;

    /** 状态;1-正常,0-禁用 */
    @ApiModelProperty(value = "状态;1-正常,0-禁用", position = 150)
    private Integer status;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 160)
    private String remark;

    /** 是否选中 */
    @ApiModelProperty(value = "是否选中;true-选中,false-未选中", position = 161)
    private Boolean checkbox;

    /** 功能号id列表 */
    @ApiModelProperty(value = "功能号id列表", position = 20)
    private List<Long> funcIds;

    /** 功能号编码列表 */
    @ApiModelProperty(value = "功能号编码列表", position = 30)
    private List<String> funcCodes;

    /** 功能号id列表 */
    @ApiModelProperty(value = "功能号详情列表", position = 50)
    private List<FuncDTO> funcs;

}
