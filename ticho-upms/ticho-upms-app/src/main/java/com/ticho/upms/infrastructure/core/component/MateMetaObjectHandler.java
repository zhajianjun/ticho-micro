package com.ticho.upms.infrastructure.core.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ticho.boot.security.util.TichoSecurityUtil;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", TichoSecurityUtil.getUserName(), metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("isDeleted", Boolean.FALSE, metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //// 针对非主键的字段,只有该表注解了fill 并且 字段名和字段属性 能匹配到才会进行填充(就算有值，也会被覆盖)
        this.setFieldValByName("updateBy", TichoSecurityUtil.getUserName(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}
