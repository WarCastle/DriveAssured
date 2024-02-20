package com.castle.drive.framework.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.castle.drive.auth.util.CommonLoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author YuLong
 * @Date 2024/2/19 10:48
 * @Classname DriveMetaObjectHandler
 * @Description 元对象处理程序
 */
@Slf4j
@Component
public class DriveMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 创建人
        try {
            this.strictInsertFill(metaObject, "createId", Long.class, CommonLoginUtil.getUserId());
        } catch (Exception e) {
        }
        // 创建时间
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 修改人
        try {
            this.strictUpdateFill(metaObject, "updateId", Long.class, CommonLoginUtil.getUserId());
        } catch (Exception e) {
        }
        // 修改时间
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

    }

}
