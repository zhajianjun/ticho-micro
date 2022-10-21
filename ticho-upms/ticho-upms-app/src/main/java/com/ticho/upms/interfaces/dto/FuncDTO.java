package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 功能号信息DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "功能号信息DTO")
public class FuncDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    @ApiModelProperty(value = "主键编号", position = 10)
    private Long id;

    /** 功能编码 */
    @ApiModelProperty(value = "功能编码", position = 20)
    private String code;

    /** 功能名称 */
    @ApiModelProperty(value = "功能名称", position = 30)
    private String name;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 40)
    private Long remark;

}
