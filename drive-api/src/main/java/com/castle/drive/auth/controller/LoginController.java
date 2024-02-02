package com.castle.drive.auth.controller;

import com.castle.drive.auth.dto.LoginDto;
import com.castle.drive.auth.service.LoginService;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.framework.response.ApiResult;
import com.castle.drive.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author YuLong
 * @Date 2024/2/1 17:38
 * @Classname LoginController
 * @Description 登录控制器
 */
@Slf4j
@RestController
@Tag(name = "管理后台登录")
@RequestMapping("/admin")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 管理后台登录
     *
     * @param loginDto
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "管理后台登录")
    public ApiResult<LoginTokenVo> login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        LoginTokenVo loginTokenVo = loginService.login(loginDto);
        // 输出token到cookie
        CookieUtil.addCookie(LoginConstant.ADMIN_COOKIE_TOKEN_NAME, loginTokenVo.getToken(), request, response);
        return ApiResult.success(loginTokenVo);
    }

    /**
     * 获取管理后台登录用户信息
     *
     * @return
     */
    @PostMapping("/getLoginUserInfo")
    @Operation(summary = "获取管理后台登录用户信息")
    public ApiResult<LoginVo> getLoginUserInfo() {
        LoginVo loginVo = loginService.getLoginUserInfo();
        return ApiResult.success(loginVo);
    }

    /**
     * 管理后台退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "管理后台退出")
    public ApiResult<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        // 删除缓存
        loginService.logout();
        // 从cookie中删除token
        CookieUtil.deleteCookie(LoginConstant.ADMIN_COOKIE_TOKEN_NAME, request, response);
        return ApiResult.success();
    }

}

