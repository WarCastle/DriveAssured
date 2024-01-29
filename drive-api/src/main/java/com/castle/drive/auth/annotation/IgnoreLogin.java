package com.castle.drive.auth.annotation;

import java.lang.annotation.*;

/**
 * @author YuLong
 * @Date 2024/1/29 16:53
 * @Classname IgnoreLogin
 * @Description 忽略登录的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogin {
}
