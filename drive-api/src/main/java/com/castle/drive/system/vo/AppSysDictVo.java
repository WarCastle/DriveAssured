package com.castle.drive.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/4 10:05
 * @Classname AppSysDictVo
 * @Description App字典数据VO
 */
@Data
@Schema(description = "App字典数据查询结果")
public class AppSysDictVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "字典名称")
    private String label;

    @Schema(description = "字典类型code")
    private String dictCode;

}
