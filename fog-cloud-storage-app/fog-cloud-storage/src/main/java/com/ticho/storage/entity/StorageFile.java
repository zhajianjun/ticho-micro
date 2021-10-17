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
 * 文件表
 *
 * @author AdoroTutto
 * @date 2021-10-17 19:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "文件表")
@TableName("storage_file")
public class StorageFile extends Model<StorageFile> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件id", position = 10)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "文件桶id", position = 20)
    private String bucketId;

    @ApiModelProperty(value = "文件名称", position = 30)
    private String name;

    @ApiModelProperty(value = "文件类型", position = 40)
    private String type;

    @ApiModelProperty(value = "文件大小。单位-KB", position = 50)
    private Integer size;

    @ApiModelProperty(value = "备注", position = 60)
    private String remark;

    @ApiModelProperty(value = "创建人", position = 70)
    private String createBy;

    @ApiModelProperty(value = "更新人", position = 80)
    private String updateBy;

    @ApiModelProperty(value = "创建时间", position = 90)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", position = 100)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标识。0-未删除,1-已删除", position = 110)
    private Integer isDeleted;

}
