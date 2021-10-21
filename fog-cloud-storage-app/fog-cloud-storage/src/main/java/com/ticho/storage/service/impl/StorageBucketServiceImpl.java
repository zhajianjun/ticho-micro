package com.ticho.storage.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.core.datasource.view.PageResult;
import com.ticho.core.mvc.exception.ServiceException;
import com.ticho.storage.entity.StorageBucket;
import com.ticho.storage.mapper.StorageBucketMapper;
import com.ticho.storage.service.StorageBucketService;
import com.ticho.storage.util.MinioTemplate;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件桶 服务实现
 *
 * @author AdoroTutto
 * @date 2021-10-21 23:47
 */
@Service
@Slf4j
public class StorageBucketServiceImpl extends ServiceImpl<StorageBucketMapper, StorageBucket> implements StorageBucketService {

    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void init() {
        List<Bucket> buckets = minioTemplate.listBuckets();
        StorageBucket storageBucket;
        List<StorageBucket> storageBuckets = new ArrayList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (Bucket bucket : buckets) {
            storageBucket = new StorageBucket();
            String bucketName = bucket.name();
            LocalDateTime creationTime = bucket.creationDate().toLocalDateTime();
            List<Result<Item>> results = minioTemplate.listObjects(bucketName, "", true);
            long count = results.stream().filter(this::isFile).count();
            storageBucket.setId(snowflake.nextId());
            storageBucket.setName(bucketName);
            storageBucket.setCreateBy("admin");
            storageBucket.setCreateTime(creationTime);
            storageBucket.setTotal(count);
            storageBucket.setIsDeleted(false);
            storageBuckets.add(storageBucket);
        }
        remove(null);
        saveBatch(storageBuckets);
    }

    private boolean isFile(Result<Item> result) {
        try {
            Item item = result.get();
            if (!item.isDir()) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(500, "同步异常");
        }
        return false;
    }

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
     * @return PageResult<StorageBucket> 文件桶 列表
     */
    @Override
    public PageResult<StorageBucket> page(int pageNum, int pageSize, StorageBucket storageBucket){
        Page<StorageBucket> page = PageHelper.startPage(pageNum, pageSize);
        baseMapper.selectList(storageBucket);
        return new PageResult<>(page);
    }


}
