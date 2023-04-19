package com.ticho.upms.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色菜单关联关系
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class RoleMenu extends Model<RoleMenu> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色id */
    private Long roleId;

    /** 菜单id */
    private Long menuId;

}