package com.castle.drive.auth.service.impl;

import com.castle.drive.auth.service.LoginRedisService;
import com.castle.drive.auth.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author YuLong
 * @Date 2024/2/1 10:22
 * @Classname LoginRedisServiceImpl
 * @Description 登录Redis服务实现类
 */
@Slf4j
@Service
public class LoginRedisServiceImpl implements LoginRedisService {

    private static final TimeUnit TOKEN_TIME_UNIT = TimeUnit.MINUTES;



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
