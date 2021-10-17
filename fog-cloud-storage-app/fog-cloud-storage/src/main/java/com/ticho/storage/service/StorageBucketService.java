package com.ticho.storage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ticho.storage.entity.StorageBucket;

import java.util.List;

/**
 * 文件桶 服务接口
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
public interface StorageBucketService extends IService<StorageBucket> {
    /**
     * 根据条件查询 文件桶 列表
     *
     * @param storageBucket 条件
     * @return List<StorageBucket> 文件桶 列表
     */
    List<StorageBucket> list(StorageBucket storageBucket);

    /**
     * 分页查询 文件桶 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param storageBucket 条件
     * @return PageInfo<StorageBucket> 文件桶 列表
     */
    PageInfo<StorageBucket> page(int pageNum, int pageSize, StorageBucket storageBucket);

}

