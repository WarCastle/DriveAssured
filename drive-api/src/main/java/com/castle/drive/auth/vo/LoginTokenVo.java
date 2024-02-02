package com.castle.drive.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author YuLong
 * @Date 2024/2/1 17:39
 * @Classname LoginTokenVo
 * @Description 登录tokenVO
 */
@Data
@Schema(description = "LoginVo")
public class LoginTokenVo {

    @Schema(description = "登录令牌")
    private String token;

}
