package com.ticho.upms.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 功能号信息
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "功能号信息")
@TableName("sys_func")
public class Func extends Model<Func> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    @ApiModelProperty(value = "主键编号", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 50)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 60)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 70)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 80)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 90)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 100)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

}