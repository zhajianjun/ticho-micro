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
 * 用户角色关联关系
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户角色关联关系")
@TableName("sys_user_role")
public class UserRole extends Model<UserRole> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 10)
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 20)
    private Long roleId;

}