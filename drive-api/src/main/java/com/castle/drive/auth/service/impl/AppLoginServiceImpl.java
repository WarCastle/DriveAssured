package com.castle.drive.auth.service.impl;

import com.castle.drive.auth.service.AppLoginService;
import com.castle.drive.auth.vo.LoginVo;

/**
 * @author YuLong
 * @Date 2024/2/1 15:27
 * @Classname AppLoginServiceImpl
 * @Description App登录服务接口实现类
 */
public class AppLoginServiceImpl implements AppLoginService {



    @Override
    public String getLoginRedisKey(String token) {
        return null;
    }

    @Override
    public void setLoginVo(String token, LoginVo loginVo) {

    }

    @Override
    public LoginVo getLoginVo(String token) {
        return null;
    }

    @Override
    public void deleteLoginVo(String token) {

    }

    @Override
    public void refreshToken() {

    }

    @Override
    public void deleteLoginInfoByToken(String token) {

    }
}
