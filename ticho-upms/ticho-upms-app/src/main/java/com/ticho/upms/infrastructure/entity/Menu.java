package com.ticho.upms.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单信息
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "菜单信息")
@TableName("sys_menu")
public class Menu extends Model<Menu> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 父级id */
    @ApiModelProperty(value = "父级id", position = 20)
    private Long parentId;

    /** 结构 */
    @ApiModelProperty(value = "结构", position = 30)
    private String structure;

    /** 类型;1-目录,2-菜单,3-权限 */
    @ApiModelProperty(value = "类型;1-目录,2-菜单,3-权限", position = 40)
    private Integer type;

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识", position = 45)
    private String perms;

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
    private Integer closable;

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

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 170)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 180)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 190)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 200)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 210)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 220)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

}