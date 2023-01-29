package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 租户信息DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "租户信息DTO")
public class TenantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号", position = 10)
    private Long id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 20)
    private String tenantId;

    /** 租户名称 */
    @ApiModelProperty(value = "租户名称", position = 30)
    private String tenantName;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 40)
    private String remark;

    /** 租户状态;1-正常,2-未激活,3-已锁定,4-已注销 */
    @ApiModelProperty(value = "租户状态;1-正常,2-未激活,3-已锁定,4-已注销", position = 50)
    private Integer status;


}
