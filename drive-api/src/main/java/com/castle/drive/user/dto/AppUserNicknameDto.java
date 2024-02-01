package com.castle.drive.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author YuLong
 * @Date 2024/2/1 16:13
 * @Classname AppUserNicknameDto
 * @Description App修改用户昵称参数
 */
@Data
@Schema(description = "App修改用户昵称参数")
public class AppUserNicknameDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

}
