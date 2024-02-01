package com.castle.drive.user.controller;

import com.castle.drive.framework.response.ApiResult;
import com.castle.drive.user.dto.AppUserHeadDto;
import com.castle.drive.user.dto.AppUserNicknameDto;
import com.castle.drive.user.service.UserService;
import com.castle.drive.user.vo.AppUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author YuLong
 * @Date 2024/2/1 17:07
 * @Classname AppUserController
 * @Description App用户信息控制器
 */
@Slf4j
@RestController
@Tag(name = "App用户信息")
@RequestMapping("/app/user")
public class AppUserController {

    @Resource
    private UserService userService;

    /**
     * 获取App用户信息
     *
     * @return
     */
    @PostMapping("/getProfile")
    @Operation(summary = "获取App用户信息")
    public ApiResult<AppUserVo> getProfile() {
        AppUserVo appUserVo = userService.getProfile();
        return ApiResult.success(appUserVo);
    }

    /**
     * 修改用户头像
     *
     * @param dto
     * @return
     */
    @PostMapping("/updateHead")
    @Operation(summary = "修改用户头像")
    public ApiResult<Boolean> updateHead(@Valid @RequestBody AppUserHeadDto dto) {
        boolean flag = userService.updateHead(dto);
        return ApiResult.result(flag);
    }

    /**
     * 修改昵称
     *
     * @param dto
     * @return
     */
    @PostMapping("/updateNickname")
    @Operation(summary = "修改昵称")
    public ApiResult<AppUserVo> updateNickname(@Valid @RequestBody AppUserNicknameDto dto) {
        boolean flag = userService.updateNickname(dto);
        return ApiResult.result(flag);
    }

}