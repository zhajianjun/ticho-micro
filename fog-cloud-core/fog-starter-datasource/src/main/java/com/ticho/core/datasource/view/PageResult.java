package com.ticho.core.datasource.view;

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

    @ApiModelProperty("页码，从1开始")
    private Integer pageNum;

    @ApiModelProperty("页面大小")
    private Integer pageSize;

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("查询数据列表")
    private List<T> rows;


    public PageResult(Page<T> page) {
        this.total = page.getTotal();
        this.rows = page.getResult();
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
    }

    public PageResult(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        this.total = page.getTotal();
        this.rows = page.getRecords();
        this.pageNum = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
    }

    public PageResult(Integer pageNum, Integer pageSize, Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
