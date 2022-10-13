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
 * 角色功能关联关系
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色功能关联关系")
@TableName("sys_role_func")
public class RoleFunc extends Model<RoleFunc> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 10)
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;

    /** 功能id */
    @ApiModelProperty(value = "功能id", position = 20)
    private Long funcId;

}