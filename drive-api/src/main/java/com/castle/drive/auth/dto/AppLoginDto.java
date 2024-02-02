package com.castle.drive.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author YuLong
 * @Date 2024/2/1 17:50
 * @Classname LoginAppDto
 * @Description 登录AppDto
 */

@Data
@Schema(description = "LoginAppDto")
public class AppLoginDto {

    @Schema(description = "小程序code")
    @NotBlank(message = "小程序code不能为空")
    private String code;

}
