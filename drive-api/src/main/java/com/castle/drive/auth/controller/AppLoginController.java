package com.castle.drive.auth.controller;

import com.castle.drive.auth.annotation.Login;
import com.castle.drive.auth.dto.AppAccountLoginDto;
import com.castle.drive.auth.dto.AppLoginDto;
import com.castle.drive.auth.service.AppLoginService;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.framework.response.ApiResult;
import com.castle.drive.util.CookieUtil;
import com.castle.drive.util.HttpServletRequestUtil;
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
 * @Date 2024/2/1 17:49
 * @Classname AppLoginController
 * @Description APP登录控制器
 */
@Slf4j
@RestController
@Tag(name = "APP登录")
@RequestMapping("/app")
public class AppLoginController {

    @Resource
    private AppLoginService appLoginService;

    /**
     * APP小程序登录
     *
     * @param appLoginDto
     * @param response
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "APP小程序登录")
    public ApiResult<LoginTokenVo> login(@Valid @RequestBody AppLoginDto appLoginDto, HttpServletRequest request, HttpServletResponse response) {
        LoginTokenVo loginTokenVo = appLoginService.login(appLoginDto);
        // 输出token到cookie
        addCookie(loginTokenVo.getToken(), request, response);
        return ApiResult.success(loginTokenVo);
    }

    /**
     * APP账号密码登录
     *
     * @param loginAccountAppDto
     * @param response
     * @return
     */
    @PostMapping("/accountLogin")
    @Operation(summary = "APP账号密码登录")
    public ApiResult<LoginTokenVo> accountLogin(@Valid @RequestBody AppAccountLoginDto loginAccountAppDto, HttpServletRequest request, HttpServletResponse response) {
        LoginTokenVo loginTokenVo = appLoginService.accountLogin(loginAccountAppDto);
        // 输出token到cookie
        addCookie(loginTokenVo.getToken(), request, response);
        return ApiResult.success(loginTokenVo);
    }

    /**
     * 获取APP登录用户信息
     *
     * @return
     */
    @Login
    @PostMapping("/getLoginUserInfo")
    @Operation(summary = "获取APP登录用户信息")
    public ApiResult<AppLoginVo> getLoginUserInfo() {
        AppLoginVo appLoginVo = appLoginService.getLoginUserInfo();
        return ApiResult.success(appLoginVo);
    }

    /**
     * APP退出
     *
     * @return
     */
    @Login
    @PostMapping("/logout")
    @Operation(summary = "APP退出")
    public ApiResult<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        appLoginService.logout();
        // 从cookie中删除token
        CookieUtil.deleteCookie(LoginConstant.APP_COOKIE_TOKEN_NAME, request, response);
        return ApiResult.success();
    }

    /**
     * 输出token到cookie
     *
     * @param token
     * @param request
     * @param response
     */
    private void addCookie(String token, HttpServletRequest request, HttpServletResponse response) {
        boolean docRequest = HttpServletRequestUtil.isDocRequest(request);
        if (docRequest) {
            CookieUtil.addCookie(LoginConstant.APP_COOKIE_TOKEN_NAME, token, request, response);
        }
    }

}
