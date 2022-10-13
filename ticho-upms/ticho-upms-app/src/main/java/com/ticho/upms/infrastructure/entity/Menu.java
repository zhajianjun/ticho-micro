package com.ticho.upms.infrastructure.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

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
    @ApiModelProperty(value = "主键编号;", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 父级id */
    @ApiModelProperty(value = "父级id", position = 20)
    private Long parentId;

    /** 父级ids */
    @ApiModelProperty(value = "父级ids", position = 30)
    private String parentIds;

    /** 标题;目录名称、菜单名称、按钮名称 */
    @ApiModelProperty(value = "标题;目录名称、菜单名称、按钮名称", position = 40)
    private String title;

    /** 类型;1-目录,2-菜单,3-按钮 */
    @ApiModelProperty(value = "类型;1-目录,2-菜单,3-按钮", position = 50)
    private String type;

    /** 权限标识（菜单和按钮） */
    @ApiModelProperty(value = "权限标识（菜单和按钮）", position = 60)
    private String permission;

    /** 后端url路径地址（菜单和按钮） */
    @ApiModelProperty(value = "后端url路径地址（菜单和按钮）", position = 70)
    private String resPath;

    /** 请求方式（GET或者POST等等） */
    @ApiModelProperty(value = "请求方式（GET或者POST等等）", position = 80)
    private String httpMethod;

    /** 路由地址（目录和菜单） */
    @ApiModelProperty(value = "路由地址（目录和菜单）", position = 90)
    private String routePath;

    /** 菜单组件名称 */
    @ApiModelProperty(value = "菜单组件名称", position = 100)
    private String componentName;

    /** 菜单组件地址 */
    @ApiModelProperty(value = "菜单组件地址", position = 110)
    private String componentPath;

    /** 状态（0、正常；1、禁用） */
    @ApiModelProperty(value = "状态（0、正常；1、禁用）", position = 120)
    private String resStatus;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 130)
    private Integer resSort;

    /** 外链菜单（1：是；2：否） */
    @ApiModelProperty(value = "外链菜单（1：是；2：否）", position = 140)
    private String menuExtFlag;

    /** 菜单缓存（1：是；2：否） */
    @ApiModelProperty(value = "菜单缓存（1：是；2：否）", position = 150)
    private String menuCacheFlag;

    /** 菜单和目录可见（1：是；2：否） */
    @ApiModelProperty(value = "菜单和目录可见（1：是；2：否）", position = 160)
    private String menuHiddenFlag;

    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标", position = 170)
    private String menuIcon;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 180)
    private String remark;

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 190)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 200)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 210)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 220)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 230)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 240)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

}