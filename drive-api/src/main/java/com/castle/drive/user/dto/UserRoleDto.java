package com.castle.drive.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/1 16:14
 * @Classname UserRoleDto
 * @Description 用户角色参数
 */
@Data
@Schema(description = "用户角色参数")
public class UserRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户角色唯一编码")
    @Length(max = 100, message = "用户角色唯一编码长度超过限制")
    private String code;

    @Schema(description = "用户角色名称")
    @NotBlank(message = "用户角色名称不能为空")
    @Length(max = 32, message = "用户角色名称长度超过限制")
    private String name;

    @Schema(description = "备注")
    @Length(max = 200, message = "备注长度超过限制")
    private String remark;

}