package com.castle.drive.util;

import java.util.UUID;

/**
 * @author YuLong
 * @Date 2024/1/30 17:44
 * @Classname UUIDUtil
 * @Description UUID工具类
 */
public class UUIDUtil {

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
