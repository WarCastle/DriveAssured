package com.castle.drive.auth.service;

import com.castle.drive.auth.vo.LoginVo;

/**
 * @author YuLong
 * @Date 2024/2/1 15:15
 * @Classname AppLoginServiceImpl
 * @Description App登录服务接口
 */
public interface AppLoginService {

    /**
     * 获取登录用户token的redis key
     * @param token
     * @return
     */
    String getLoginRedisKey(String token);

    /**
     * 设置登录用户信息到redis
     * @param token
     * @param loginVo
     */
    void setLoginVo(String token, LoginVo loginVo);

    /**
     * 获取redis中的登录用户信息
     * @param token
     * @return
     */
    LoginVo getLoginVo(String token);

    /**
     * 删除redis中的登录用户信息
     * @param token
     */
    void deleteLoginVo(String token);

    /**
     * 刷新token
     */
    void refreshToken();

    /**
     * 通过用户token删除当前用户之前的所有redis登录信息
     * @param token
     */
    void deleteLoginInfoByToken(String token);
}
