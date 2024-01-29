package com.castle.drive.auth.annotation;

import java.lang.annotation.*;

/**
 * @author YuLong
 * @Date 2024/1/29 16:45
 * @Classname AppUserRole
 * @Description 检查App端用户角色
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Login
public @interface AppUserRole {

    /**
     * 角色ID或结算编码
     * @return
     */
    String[] value() default {};
}
