package com.castle.drive.common.enums;

import lombok.Getter;

import java.util.*;

/**
 * @author YuLong
 * @Date 2024/2/5 13:51
 * @Classname UploadType
 * @Description 文件上传业务类型
 */
@Getter
public enum UploadType {

    ANY("any", 100, null, "任意文件不限制上传"),
    IMAGE("image", 20, Arrays.asList("jpg", "jpeg", "png"), "仅图片格式上传"),
    HEAD("head", 10, Arrays.asList("jpg", "jpeg", "png"), "用户头像上传"),
    EXCEL("excel", 100, Arrays.asList("xls", "xlsx"), "仅Excel表格上传"),
    WORD("word", 100, Arrays.asList("doc", "docx"), "仅Word文档上传"),
    PDF("pdf", 100, Collections.singletonList("pdf"), "仅PDF上传");
    // 更多自定义即可

    private final String type;
    private final Integer maxSizeMb;

    /**
     * 文件后缀都使用小写
     */
    private final List<String> extensions;
    private final String desc;

    UploadType(String type, Integer maxSizeMb, List<String> extensions, String desc) {
        this.type = type;
        this.maxSizeMb = maxSizeMb;
        this.extensions = extensions;
        this.desc = desc;
    }

    private static final Map<String, UploadType> map = new HashMap<>();

    static {
        for (UploadType upLoadType : values()) {
            map.put(upLoadType.type, upLoadType);
        }
    }

    public static String getCode(UploadType type) {
        if (Objects.isNull(type)) {
            return null;
        }
        return type.type;
    }

    public static UploadType get(String code) {
        UploadType uploadType = map.get(code);
        if (Objects.isNull(uploadType)) {
            return null;
        }
        return uploadType;
    }

    public static String getDesc(String code) {
        UploadType uploadType = get(code);
        if (Objects.isNull(uploadType)) {
            return null;
        }
        return uploadType.getDesc();
    }

}
