package com.castle.drive.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/4 10:00
 * @Classname SysDictTypeVo
 * @Description 字典类型VO
 */
@Data
@Schema(description = "字典类型查询结果")
public class SysDictTypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "字典类型编码")
    private String code;

    @Schema(description = "字典类型名称")
    private String name;

    @Schema(description = "是否是系统字典类型")
    private Boolean isSystem;

    @Schema(description = "备注")
    private String remark;

}