package com.ticho.core.datasource.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 通用分页查询条件
 *
 * @author AdoroTutto
 * @date 2020-09-14 22:42
 */
@Data
@ApiModel("基础分页查询条件")
public class BasePageQuery {

    @ApiModelProperty(value = "当前页码", required = true, example = "1", position = 1)
    @NotNull(message = "页码，从1开始")
    @Min(value = 1, message = "当前页码最小值为1")
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小", required = true, example = "10", position = 5)
    @NotNull(message = "页面大小不能为空")
    @Min(value = 1, message = "页面大小最小值为1")
    private Integer pageSize;

}
