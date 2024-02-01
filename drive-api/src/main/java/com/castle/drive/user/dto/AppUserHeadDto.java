package com.castle.drive.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/1 16:12
 * @Classname AppUserHeadDto
 * @Description App修改用户头像参数
 */
@Data
@Schema(description = "App修改用户头像参数")
public class AppUserHeadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "头像")
    @NotBlank(message = "头像不能为空")
    private String head;

}
