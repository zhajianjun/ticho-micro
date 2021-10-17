package com.ticho.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.storage.entity.StorageFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件表 mapper
 *
 * @author AdoroTutto
 * @date 2021-10-17 19:48
 */
@Repository
public interface StorageFileMapper extends BaseMapper<StorageFile> {

    /**
     * 根据条件查询 文件表 列表
     *
     * @param storageFile 条件
     * @return List<StorageFile> 文件表 列表
     */
    List<StorageFile> selectList(StorageFile storageFile);

}