package com.ticho.upms.interfaces.query;

import java.io.Serializable;
import com.ticho.boot.view.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户角色关联关系查询条件
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户角色关联关系查询条件")
public class UserRoleQuery extends BasePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 10)
    private Long userId;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 20)
    private Long roleId;

}
