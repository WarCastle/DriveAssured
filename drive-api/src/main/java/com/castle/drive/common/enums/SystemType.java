package com.castle.drive.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/31 14:09
 * @Classname SystemType
 * @Description 系统类型枚举
 */
@Getter
public enum SystemType {

    ADMIN(1, "Admin管理后台"),
    APP(2, "App客户端");

    private final Integer code;
    private final String desc;

    SystemType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, SystemType> map = new HashMap<>();

    static {
        for (SystemType type : values()) {
            map.put(type.code, type);
        }
    }

    public static SystemType get(Integer code) {
        SystemType type = map.get(code);
        if (Objects.isNull(type)) {
            return null;
        }
        return type;
    }

    public static Integer getCode(SystemType type) {
        if (Objects.isNull(type)) {
            return null;
        }
        return type.code;
    }

    public static String getDesc(Integer code) {
        SystemType type = get(code);
        if (Objects.isNull(type)) {
            return null;
        }
        return type.getDesc();
    }

}
