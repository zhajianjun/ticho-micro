package com.ticho.upms.interfaces.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

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
    private String id;

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
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 70)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 80)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 90)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 100)
    private Integer isDelete;

}
