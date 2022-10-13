package com.ticho.upms.infrastructure.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 数据字典
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "数据字典")
@TableName("sys_dict")
public class Dict extends Model<Dict> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号;", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 110)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 120)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 130)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 140)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 150)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 160)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

}