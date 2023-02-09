package com.ticho.upms.interfaces.dto;

import com.ticho.boot.web.util.valid.ValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单信息DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "菜单信息DTO")
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号", position = 10)
    @NotNull(message = "id不能为空", groups = {ValidGroup.Upd.class})
    private Long id;

    /** 父级id */
    @ApiModelProperty(value = "父级id", position = 20)
    @NotNull(message = "父id不能为空", groups = {ValidGroup.Add.class})
    private Long parentId;

    /** 结构 */
    @ApiModelProperty(value = "结构", position = 30)
    private String structure;

    /** 类型;1-目录,2-菜单,3-权限 */
    @ApiModelProperty(value = "类型;1-目录,2-菜单,3-权限", position = 40)
    @NotNull(message = "类型不能为空", groups = {ValidGroup.Add.class})
    private Integer type;

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识", position = 45)
    private List<String> perms;

    /** 标题;目录名称、菜单名称 */
    @ApiModelProperty(value = "标题;目录名称、菜单名称", position = 50)
    @NotNull(message = "标题不能为空", groups = {ValidGroup.Add.class})
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
    @NotNull(message = "是否外链菜单不能为空", groups = {ValidGroup.Add.class})
    private Integer extFlag;

    /** 是否缓存;1-是,0-否 */
    @ApiModelProperty(value = "是否缓存;1-是,0-否", position = 100)
    @NotNull(message = "是否缓存不能为空", groups = {ValidGroup.Add.class})
    private Integer cacheAble;

    /** 菜单和目录是否可见;1-是,0-否 */
    @ApiModelProperty(value = "菜单和目录是否可见;1-是,0-否", position = 110)
    @NotNull(message = "菜单和目录是否可见不能为空", groups = {ValidGroup.Add.class})
    private Integer invisible;

    /** 菜单是否可关闭;1-是,0-否 */
    @ApiModelProperty(value = "菜单是否可关闭;1-是,0-否", position = 120)
    @NotNull(message = "菜单是否可关闭不能为空", groups = {ValidGroup.Add.class})
    private Integer closable;

    /** 图标 */
    @ApiModelProperty(value = "图标", position = 130)
    private String icon;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 140)
    @NotNull(message = "排序不能为空", groups = {ValidGroup.Add.class})
    private Integer sort;

    /** 状态;1-正常,0-禁用 */
    @ApiModelProperty(value = "状态;1-正常,0-禁用", position = 150)
    private Integer status;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 160)
    private String remark;


    public interface Drectory {
    }

    public interface Menu {
    }


}
