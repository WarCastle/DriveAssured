package com.castle.drive.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YuLong
 * @Date 2024/2/4 9:56
 * @Classname SysDictVo
 * @Description 系统词典VO
 */
@Data
@Schema(description = "字典数据查询结果")
public class SysDictVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "字典值")
    private String value;

    @Schema(description = "字典名称")
    private String label;

    @Schema(description = "字典类型code")
    private String dictCode;

    @Schema(description = "状态 1：启用，0：禁用")
    private Boolean status;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人ID")
    private Long createId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改人ID")
    private Long updateId;

    @Schema(description = "修改时间")
    private Date updateTime;

}