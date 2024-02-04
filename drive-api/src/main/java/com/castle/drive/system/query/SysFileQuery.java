package com.castle.drive.system.query;

import com.castle.drive.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author YuLong
 * @Date 2024/2/4 10:18
 * @Classname SysFileQuery
 * @Description 系统文件查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统文件查询参数")
public class SysFileQuery extends BasePageQuery {
    private static final long serialVersionUID = 1L;

    @Schema(description = "文件类型 1：图片，2：音视频，3：文档，4：文件")
    private Integer fileType;

    @Schema(description = "创建开始时间")
    private Date createStartTime;

    @Schema(description = "创建结束时间")
    private Date createEndTime;

}
