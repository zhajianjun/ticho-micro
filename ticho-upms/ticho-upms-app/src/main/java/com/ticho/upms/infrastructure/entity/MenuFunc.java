package com.ticho.upms.infrastructure.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 菜单功能关联关系
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "菜单功能关联关系")
@TableName("sys_menu_func")
public class MenuFunc extends Model<MenuFunc> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 菜单id */
    @ApiModelProperty(value = "菜单id", position = 10)
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 20)
    private Long funcId;

}