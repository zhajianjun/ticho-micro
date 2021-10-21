package com.ticho.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ticho.storage.entity.StorageBucket;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件桶 mapper
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@Repository
public interface StorageBucketMapper extends BaseMapper<StorageBucket> {

    /**
     * 根据条件查询 文件桶 列表
     *
     * @param storageBucket 条件
     * @return List<StorageBucket> 文件桶 列表
     */
    List<StorageBucket> selectList(StorageBucket storageBucket);

}