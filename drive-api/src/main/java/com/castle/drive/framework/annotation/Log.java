package com.castle.drive.framework.annotation;

import com.castle.drive.common.enums.SysLogType;

/**
 * @author YuLong
 * @Date 2024/2/1 17:12
 * @Classname Log
 * @Description 日志注解
 */
public @interface Log {

    /**
     * 描述
     *
     * @return
     */
    String value() default "";

    SysLogType type() default SysLogType.OTHER;
}
