package com.ticho.core.datasource.view;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @ApiModelProperty(value = "页码，从1开始", position = 10)
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小", position = 20)
    private Integer pageSize;

    @ApiModelProperty(value = "总页数", position = 30)
    private Integer pages;

    @ApiModelProperty(value = "总数", position = 40)
    private Long total;

    @ApiModelProperty(value = "查询数据列表", position = 50)
    private List<T> rows;


    public PageResult(Page<T> page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.pages = page.getPages();
        this.total = page.getTotal();
        this.rows = page.getResult();
    }

    public PageResult(com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> page) {
        this.pageNum = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
        this.pages = (int) page.getPages();
        this.total = page.getTotal();
        this.rows = page.getRecords();

    }

    public PageResult(int pageNum, int pageSize, long total, List<T> rows) {
        // @formatter:off
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = pageSize <= 0 ? 0 : (int) Math.ceil(BigDecimal.valueOf(total).divide(BigDecimal.valueOf(pageSize), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        this.total = total;
        this.rows = rows;
        // @formatter:on
    }
}
