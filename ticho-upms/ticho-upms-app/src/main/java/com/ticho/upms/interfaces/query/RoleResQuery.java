package com.ticho.upms.interfaces.query;

import java.io.Serializable;
import com.ticho.boot.view.core.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色和资源关联表查询条件
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "角色和资源关联表查询条件")
public class RoleResQuery extends BasePageQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**  */
    @ApiModelProperty(value = "", position = 10)
    private String roleId;

    /**  */
    @ApiModelProperty(value = "", position = 20)
    private String resId;

}
