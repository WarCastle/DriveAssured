package com.castle.drive.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/4 10:07
 * @Classname SysDictAppQuery
 * @Description App字典数据查询参数
 */
@Data
@Schema(description = "App字典数据查询参数")
public class SysDictAppQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型编码")
    private String dictCode;

}
