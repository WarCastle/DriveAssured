package com.castle.drive.common.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/5 11:22
 * @Classname FileServerType
 * @Description 文件服务类型
 */
@Getter
public enum FileServerType {

    LOCAL(1, "本地文件服务"),
    OSS(2, "OSS文件服务");

    private final Integer code;
    private final String desc;

    FileServerType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static final Map<Integer, FileServerType> map = new HashMap<>();

    static {
        for (FileServerType type : values()) {
            map.put(type.code, type);
        }
    }

    public static Integer getCode(FileServerType type) {
        if (Objects.isNull(type)) {
            return LOCAL.code;
        }
        return type.code;
    }

    public static FileServerType get(Integer code) {
        FileServerType type = map.get(code);
        if (Objects.isNull(type)) {
            return LOCAL;
        }
        return type;
    }

    public static String getDesc(Integer code) {
        FileServerType fileServerType = get(code);
        if (Objects.isNull(fileServerType)) {
            return null;
        }
        return fileServerType.getDesc();
    }

}
