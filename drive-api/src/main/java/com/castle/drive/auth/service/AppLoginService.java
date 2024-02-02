package com.castle.drive.auth.service;

import com.castle.drive.auth.dto.AppAccountLoginDto;
import com.castle.drive.auth.dto.AppLoginDto;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.user.entity.User;

import java.util.Date;

/**
 * @author YuLong
 * @Date 2024/2/1 15:15
 * @Classname AppLoginServiceImpl
 * @Description App登录服务接口
 */
public interface AppLoginService {

    /**
     * APP小程序登录
     *
     * @param dto
     * @return
     */
    LoginTokenVo login(AppLoginDto dto);

    /**
     * APP账号密码登录
     *
     * @param dto
     * @return
     */
    LoginTokenVo accountLogin(AppAccountLoginDto dto);

    /**
     * APP登录
     *
     * @param user
     * @return
     */
    LoginTokenVo login(User user);

    /**
     * 刷新登录信息
     *
     * @param user
     * @param token
     * @param lastLoginTime
     * @return
     */
    AppLoginVo refreshLoginInfo(User user, String token, Date lastLoginTime);

    /**
     * 获取登录用户信息
     *
     * @return
     */
    AppLoginVo getLoginUserInfo();

    /**
     * 登出
     */
    void logout();

}
