package com.ticho.common.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 权限标识信息DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "权限标识信息DTO")
public class PermDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 编码 */
    @ApiModelProperty(value = "编码", position = 10)
    private String code;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 20)
    private String name;

}
