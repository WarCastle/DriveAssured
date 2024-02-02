package com.castle.drive.auth.service;

import com.castle.drive.auth.dto.LoginDto;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.system.entity.SysUser;

import java.util.Date;

/**
 * @author YuLong
 * @Date 2024/2/1 17:39
 * @Classname LoginService
 * @Description 登录服务接口
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param dto
     * @return
     */
    LoginTokenVo login(LoginDto dto);

    /**
     * 处理登录用户信息
     *
     * @param sysUser
     * @param token
     * @param loginTime
     * @return
     */
    LoginVo refreshLoginInfo(SysUser sysUser, String token, Date loginTime);

    /**
     * 获取登录用户信息
     *
     * @return
     */
    LoginVo getLoginUserInfo();

    /**
     * 登出
     *
     */
    void logout();

}
