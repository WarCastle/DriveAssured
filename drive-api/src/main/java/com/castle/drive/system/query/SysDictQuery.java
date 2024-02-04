package com.castle.drive.system.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author YuLong
 * @Date 2024/2/4 10:03
 * @Classname SysDictQuery
 * @Description 字典数据查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典数据查询参数")
public class SysDictQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;

    @Schema(description = "字典类型code")
    @NotBlank(message = "字典类型不能为空")
    private String dictCode;

    @Schema(description = "状态 1：启用，0：禁用")
    private Boolean status;

}