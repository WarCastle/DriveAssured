package com.castle.drive.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author YuLong
 * @Date 2024/2/5 11:47
 * @Classname UploadVo
 * @Description 文件上传VO
 */
@Data
@Schema(description = "文件上传VO")
public class UploadVo {

    @Schema(description = "文件ID")
    private Long id;

    @Schema(description = "源文件名称")
    private String originalFilename;

    @Schema(description = "文件名称")
    private String filename;

    @Schema(description = "文件路径")
    private String url;

    @Schema(description = "文件大小")
    private Long size;

    @Schema(description = "文件大小MB")
    private BigDecimal sizeMb;

}
