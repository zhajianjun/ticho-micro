package com.ticho.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ticho.storage.entity.StorageFile;

import java.util.List;

/**
 * 文件表 服务接口
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
public interface StorageFileService extends IService<StorageFile> {
    /**
     * 根据条件查询 文件表 列表
     *
     * @param storageFile 条件
     * @return List<StorageFile> 文件表 列表
     */
    List<StorageFile> list(StorageFile storageFile);

    /**
     * 分页查询 文件表 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param storageFile 条件
     * @return PageInfo<StorageFile> 文件表 列表
     */
    PageInfo<StorageFile> page(int pageNum, int pageSize, StorageFile storageFile);

}

