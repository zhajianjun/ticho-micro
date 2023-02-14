package com.ticho.upms.interfaces.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色菜单查询条件
 *
 * @author zhajianjun
 * @date 2023-02-07 15:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色菜单查询条件")
public class RoleDtlQuery {

    /** 角色code */
    @ApiModelProperty(value = "角色id列表", position = 10)
    private List<Long> roleIds;

    /** 租户编号 */
    @ApiModelProperty(value = "租户编号", position = 20)
    private String tenantId;

    /** 角色code */
    @ApiModelProperty(value = "角色code列表", position = 30)
    private List<String> roleCodes;

    /** 显示所有 */
    @ApiModelProperty(value = "是否显示所有", position = 40)
    private Boolean showAll = false;

}
