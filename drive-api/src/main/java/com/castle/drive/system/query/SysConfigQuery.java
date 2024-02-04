package com.castle.drive.system.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YuLong
 * @Date 2024/2/2 17:09
 * @Classname SysConfigQuery
 * @Description 系统配置查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统配置查询参数")
public class SysConfigQuery extends BasePageQuery {
    private static final long serialVersionUID = 1L;

    @Schema(description = "是否是系统字典类型")
    private Boolean isSystem;

    @Schema(description = "状态 1：正常，0：禁用")
    private Boolean status;

}
