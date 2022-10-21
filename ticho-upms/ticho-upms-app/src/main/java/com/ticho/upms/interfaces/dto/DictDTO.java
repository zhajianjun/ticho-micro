package com.ticho.upms.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 数据字典DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "数据字典DTO")
public class DictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号;", position = 10)
    private Integer id;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 20)
    private Integer pid;

    /** 字典类型id */
    @ApiModelProperty(value = "字典类型id", position = 30)
    private Long typeId;

    /** 字典编码 */
    @ApiModelProperty(value = "字典编码", position = 40)
    private String code;

    /** 字典名称 */
    @ApiModelProperty(value = "字典名称", position = 50)
    private String name;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 60)
    private Integer sort;

    /** 层级 */
    @ApiModelProperty(value = "层级", position = 70)
    private Integer level;

    /** 结构 */
    @ApiModelProperty(value = "结构", position = 80)
    private String structure;

    /** 状态;0-禁用,1-正常 */
    @ApiModelProperty(value = "状态;0-禁用,1-正常", position = 90)
    private Integer status;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 100)
    private String remark;

}
