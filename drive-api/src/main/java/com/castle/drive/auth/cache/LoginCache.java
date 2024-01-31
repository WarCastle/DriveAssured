package com.castle.drive.auth.cache;

import com.castle.drive.auth.vo.LoginVo;

/**
 * @author YuLong
 * @Date 2024/1/30 17:59
 * @Classname LoginCache
 * @Description 登录缓存
 */
public class LoginCache {

    /**
     * 当前线程中保存管理后台登录信息
     */
    private static final ThreadLocal<LoginVo> LOGIN_CACHE = new ThreadLocal<>();

    /**
     * 设置管理后台登录信息到当前线程中
     * @param loginVo
     */
    public static void set(LoginVo loginVo){
        LOGIN_CACHE.set(loginVo);
    }

    /**
     * 获取当前线程中的管理后台登录信息
     * @return
     */
    public static LoginVo get(){
        return LOGIN_CACHE.get();
    }

    /**
     * 移除当前线程中的管理后台登录信息
     */
    public static void remove(){
        LOGIN_CACHE.remove();
    }
}
