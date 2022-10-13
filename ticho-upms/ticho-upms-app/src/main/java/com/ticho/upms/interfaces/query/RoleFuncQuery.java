package com.ticho.upms.interfaces.query;

import java.io.Serializable;
import com.ticho.boot.view.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色功能关联关系查询条件
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色功能关联关系查询条件")
public class RoleFuncQuery extends BasePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 10)
    private Long roleId;

    /** 功能id */
    @ApiModelProperty(value = "功能id", position = 20)
    private Long funcId;

}
