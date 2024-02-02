package com.castle.drive.auth.cache;

import com.castle.drive.auth.vo.AppLoginVo;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/1/29 18:03
 * @Classname AppLoginCache
 * @Description App登录缓存
 *  在当前线程中缓存token
 *  如果开启多线程需要获取
 */
@Component
public class AppLoginCache {

    /**
     * 当前线程中保存APP移动端登录信息
     */
    private static final ThreadLocal<AppLoginVo> APP_LOGIN_CACHE = new ThreadLocal<>();

    /**
     * 设置APP移动端登录信息到当前线程中
     *
     * @param appLoginVo
     */
    public static void set(AppLoginVo appLoginVo) {
        APP_LOGIN_CACHE.set(appLoginVo);
    }

    /**
     * 获取当前线程中的APP移动端登录信息
     *
     * @return
     */
    public static AppLoginVo get() {
        return APP_LOGIN_CACHE.get();
    }

    /**
     * 移除当前线程中的APP移动端登录信息
     */
    public static void remove() {
        APP_LOGIN_CACHE.remove();
    }

}
