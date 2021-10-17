package com.ticho.datasource.view;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author AdoroTutto
 * @date 2021-10-18 0:01
 */
@Data
@ApiModel("分页对象")
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("总条数")
    private long total;

    @ApiModelProperty("分页结果list")
    private List<T> list;

    public PageResult(Page<T> list) {
        this.total = list.getTotal();
        this.list = list.getResult();
    }

    public PageResult(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> list) {
        this.total = list.getTotal();
        this.list = list.getRecords();
    }

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
