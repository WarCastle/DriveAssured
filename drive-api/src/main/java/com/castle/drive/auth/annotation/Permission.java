package com.castle.drive.auth.annotation;

import java.lang.annotation.*;

/**
 * @author YuLong
 * @Date 2024/1/29 16:55
 * @Classname Permission
 * @Description 权限注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * 权限编码
     * @return
     */
    String value();

    /**
     * 角色编码
     * @return
     */
    String role() default "";

    /**
     * 角色编码数组
     * @return
     */
    String[] roles() default {};
}
