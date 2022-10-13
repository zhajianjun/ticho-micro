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
 * 角色和资源关联表
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色和资源关联表")
@TableName("sys_role_res")
public class RoleRes extends Model<RoleRes> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    @ApiModelProperty(value = "", position = 10)
    private String roleId;

    /**  */
    @ApiModelProperty(value = "", position = 20)
    @TableId(value = "res_id", type = IdType.ASSIGN_ID)
    private String resId;

}