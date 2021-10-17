package com.ticho.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ticho.storage.entity.StorageBucket;
import com.ticho.storage.mapper.StorageBucketMapper;
import com.ticho.storage.service.StorageBucketService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件桶 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-17 23:59
 */
@Service
public class StorageBucketServiceImpl extends ServiceImpl<StorageBucketMapper, StorageBucket> implements StorageBucketService {

    /**
     * 根据条件查询 文件桶 列表
     *
     * @param storageBucket 条件
     * @return List<StorageBucket> 文件桶 列表
     */
    @Override
    public List<StorageBucket> list(StorageBucket storageBucket) {
        return baseMapper.selectList(storageBucket);
    }

    /**
     * 分页查询 文件桶 列表
     *
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @param storageBucket 条件
     * @return PageInfo<StorageBucket> 文件桶 列表
     */
    @Override
    public PageInfo<StorageBucket> page(int pageNum, int pageSize, StorageBucket storageBucket){
        PageHelper.startPage(pageNum, pageSize);
        List<StorageBucket> storageBuckets = baseMapper.selectList(storageBucket);
        return new PageInfo<>(storageBuckets);
    }


}
