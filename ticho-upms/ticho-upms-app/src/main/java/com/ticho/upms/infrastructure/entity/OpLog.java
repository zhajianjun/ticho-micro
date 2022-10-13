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

/**
 * 日志信息
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "日志信息")
@TableName("sys_op_log")
public class OpLog extends Model<OpLog> implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号; */
    @ApiModelProperty(value = "主键编号;", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 请求地址 */
    @ApiModelProperty(value = "请求地址", position = 20)
    private String url;

    /** 日志类型 */
    @ApiModelProperty(value = "日志类型", position = 30)
    private String type;

    /** 请求方法 */
    @ApiModelProperty(value = "请求方法", position = 40)
    private String method;

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数", position = 50)
    private String params;

    /** 内容 */
    @ApiModelProperty(value = "内容", position = 60)
    private String message;

    /** 总耗时长（毫秒） */
    @ApiModelProperty(value = "总耗时长（毫秒）", position = 70)
    private Integer totalTime;

    /** 请求IP */
    @ApiModelProperty(value = "请求IP", position = 80)
    private String ip;

    /** 操作人 */
    @ApiModelProperty(value = "操作人", position = 90)
    private String operateBy;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 100)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime;

    /** 是否异常 */
    @ApiModelProperty(value = "是否异常", position = 110)
    private Integer isErr;

    /** 异常信息 */
    @ApiModelProperty(value = "异常信息", position = 120)
    private String errMessage;

}