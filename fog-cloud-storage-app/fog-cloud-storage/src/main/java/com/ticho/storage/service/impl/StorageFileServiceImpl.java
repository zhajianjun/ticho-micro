package com.ticho.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ticho.storage.entity.StorageFile;
import com.ticho.storage.mapper.StorageFileMapper;
import com.ticho.storage.service.StorageFileService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件表 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
@Service
public class StorageFileServiceImpl extends ServiceImpl<StorageFileMapper, StorageFile> implements StorageFileService {

    /**
     * 根据条件查询 文件表 列表
     *
     * @param storageFile 条件
     * @return List<StorageFile> 文件表 列表
     */
    @Override
    public List<StorageFile> list(StorageFile storageFile) {
        return baseMapper.selectList(storageFile);
    }

    /**
     * 分页查询 文件表 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param storageFile 条件
     * @return PageInfo<StorageFile> 文件表 列表
     */
    @Override
    public PageInfo<StorageFile> page(int pageNum, int pageSize, StorageFile storageFile){
        PageHelper.startPage(pageNum, pageSize);
        List<StorageFile> storageFiles = baseMapper.selectList(storageFile);
        return new PageInfo<>(storageFiles);
    }


}
