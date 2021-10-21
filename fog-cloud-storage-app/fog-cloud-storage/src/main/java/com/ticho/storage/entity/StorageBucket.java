package com.ticho.storage.entity;

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
 * 文件桶
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "文件桶")
@TableName("storage_bucket")
public class StorageBucket extends Model<StorageBucket> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件桶id", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "文件桶名称", position = 20)
    private String name;

    @ApiModelProperty(value = "文件个数", position = 30)
    private Long total;

    @ApiModelProperty(value = "备注", position = 40)
    private String remark;

    @ApiModelProperty(value = "创建人", position = 50)
    private String createBy;

    @ApiModelProperty(value = "创建时间", position = 60)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人", position = 70)
    private String updateBy;

    @ApiModelProperty(value = "修改时间", position = 80)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标识。0-未删除,1-已删除", position = 90)
    private Boolean isDeleted;

}
