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
 * 租户信息
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "租户信息")
@TableName("sys_tenant")
public class Tenant extends Model<Tenant> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号;", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 60)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 70)
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 80)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 90)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 100)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 110)
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

}