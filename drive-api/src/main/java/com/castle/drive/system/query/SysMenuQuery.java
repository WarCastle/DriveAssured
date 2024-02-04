package com.castle.drive.system.query;

import com.castle.drive.framework.query.DataRangeQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YuLong
 * @Date 2024/2/2 17:01
 * @Classname SysMenuQuery
 * @Description 系统菜单查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统菜单查询参数")
public class SysMenuQuery extends DataRangeQuery {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单类型，1：目录，2：菜单，3：权限")
    private Integer type;

    @Schema(description = "状态，0：禁用，1：启用")
    private Boolean status;

    @Schema(description = "是否显示,0：不显示，1：显示")
    private Boolean isShow;

    @Schema(description = "关键字搜索")
    private String keyword;

}
