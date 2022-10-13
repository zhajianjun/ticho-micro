package com.ticho.upms.interfaces.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据字典类型DTO
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "数据字典类型DTO")
public class DictTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    @ApiModelProperty(value = "主键编号", position = 10)
    private Long id;

    /** 类型编码 */
    @ApiModelProperty(value = "类型编码", position = 20)
    private String code;

    /** 类型名称 */
    @ApiModelProperty(value = "类型名称", position = 30)
    private String name;

    /** 是否系统字典;1-是,0-否 */
    @ApiModelProperty(value = "是否系统字典;1-是,0-否", position = 40)
    private Integer isSys;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 50)
    private String remark;

    /** 乐观锁;控制版本更改 */
    @ApiModelProperty(value = "乐观锁;控制版本更改", position = 60)
    private Long version;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 70)
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 80)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 90)
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 100)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /** 删除标识;0-未删除,1-已删除 */
    @ApiModelProperty(value = "删除标识;0-未删除,1-已删除", position = 110)
    private Integer isDelete;

}
