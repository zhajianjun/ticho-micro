package com.ticho.upms.interfaces.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色和资源关联表DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色和资源关联表DTO")
public class RoleResDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    @ApiModelProperty(value = "", position = 10)
    private String roleId;

    /**  */
    @ApiModelProperty(value = "", position = 20)
    private String resId;

}
