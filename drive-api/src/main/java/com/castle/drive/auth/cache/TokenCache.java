package com.castle.drive.auth.cache;

/**
 * @author YuLong
 * @Date 2024/1/30 18:18
 * @Classname TokenCache
 * @Description token缓存
 */
public class TokenCache {

    /**
     * 当前线程中保存token值
     */
    private static final ThreadLocal<String> TOKEN_CACHE = new ThreadLocal<>();

    /**
     * 设置token值到当前线程中
     * @param token
     */
    public static void set(String token) {
        TOKEN_CACHE.set(token);
    }

    /**
     * 获取当前线程中的token值
     * @return
     */
    public static String get() {
        return TOKEN_CACHE.get();
    }

    /**
     * 移除当前线程中的token值
     */
    public static void remove() {
        TOKEN_CACHE.remove();
    }
}
