package com.castle.drive.auth.service;

import com.castle.drive.auth.vo.AppLoginVo;

/**
 * @author YuLong
 * @Date 2024/1/29 17:44
 * @Classname AppLoginRedisService
 * @Description App登录Redis服务接口
 */
public interface AppLoginRedisService {

    /**
     * 获取登录用户token的redis key
     * @param token
     * @return
     */
    String getLoginRedisKey(String token);

    /**
     * 设置登录用户信息到redis
     * @param token
     * @param appLoginVo
     */
    void setLoginVo(String token, AppLoginVo appLoginVo);

    /**
     * 获取redis中的登录用户信息
     * @param token
     * @return
     */
    AppLoginVo getLoginVo(String token);

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
     * 通过用户token删除当前用户之前的所有的redis登录信息
     * @param token
     */
    void deleteLoginInfoByToken(String token);
}
