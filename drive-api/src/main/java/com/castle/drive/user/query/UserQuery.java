package com.castle.drive.user.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YuLong
 * @Date 2024/2/1 16:08
 * @Classname UserQuery
 * @Description 用户信息查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户信息查询参数")
public class UserQuery extends BasePageQuery {

    private static final long serialVersionUID = 1L;

}