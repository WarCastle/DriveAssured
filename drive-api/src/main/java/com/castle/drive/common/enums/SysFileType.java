package com.castle.drive.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/5 14:55
 * @Classname SysFileType
 * @Description 文件类型 1：图片，2：音视频，3：文档，4：文件
 */
@Getter
public enum SysFileType {

    IMAGE(1, "图片"),
    VIDEO(2, "音视频"),
    OFFICE(3, "文档"),
    FILE(4, "文件");

    private final Integer code;
    private final String desc;

    SysFileType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, SysFileType> map = new HashMap<>();

    static {
        for (SysFileType type : values()) {
            map.put(type.code, type);
        }
    }

    public static Integer getCode(SysFileType type) {
        if (Objects.isNull(type)) {
            return null;
        }
        return type.code;
    }

    public static SysFileType get(Integer code) {
        SysFileType type = map.get(code);
        if (Objects.isNull(type)) {
            return null;
        }
        return type;
    }

    public static String getDesc(Integer code) {
        SysFileType type = get(code);
        if (Objects.isNull(type)) {
            return null;
        }
        return type.getDesc();
    }

}
