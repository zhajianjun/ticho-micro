package com.ticho.core.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充时间字段
 *
 * @author AdoroTutto
 * @date 2021-10-18 0:01
 */
@Component
public class MateMetaObjectHandler implements MetaObjectHandler {
    public static final String ADMIN = "admin";

    @Override
    public void insertFill(MetaObject metaObject) {
        // 针对非主键的字段,只有该表注解了fill 并且 字段名和字段属性 能匹配到才会进行填充(null 值不填充)
        this.strictInsertFill(metaObject, "createBy", String.class, ADMIN);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "isDeleted", Boolean.class, Boolean.FALSE);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //// 针对非主键的字段,只有该表注解了fill 并且 字段名和字段属性 能匹配到才会进行填充(就算有值，也会被覆盖)
        this.setFieldValByName("updateBy", ADMIN, metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        //this.strictUpdateFill(metaObject, "updateBy", String.class, ADMIN);
        //this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
