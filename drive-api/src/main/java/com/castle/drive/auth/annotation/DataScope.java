package com.castle.drive.auth.annotation;

import java.lang.annotation.*;

/**
 * @author YuLong
 * @Date 2024/1/29 16:49
 * @Classname DataScope
 * @Description 数据权限范围注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 用户表别名
     * @return
     */
    String userAlias() default "";

    /**
     * 筛选的userId列
     * @return
     */
    String userIdColumn() default "id";
}
