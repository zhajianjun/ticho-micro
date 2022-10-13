package com.ticho.upms.interfaces.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户角色关联关系DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户角色关联关系DTO")
public class UserRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 10)
    private Long userId;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 20)
    private Long roleId;

}
