package com.castle.drive.system.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YuLong
 * @Date 2024/2/4 10:01
 * @Classname SysDictTypeQuery
 * @Description 字典类型查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典类型查询参数")
public class SysDictTypeQuery extends BasePageQuery {
    private static final long serialVersionUID = 1L;

}
