package com.castle.drive.system.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YuLong
 * @Date 2024/2/4 9:54
 * @Classname SysRoleQuery
 * @Description 系统角色查询
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色查询参数")
public class SysRoleQuery extends BasePageQuery {
    private static final long serialVersionUID = 1L;

    @Schema(description = "是否系统内置角色 1：是，0：否")
    private Boolean isSystem;

}
