package com.castle.drive.auth.util;

import com.castle.drive.auth.cache.AppLoginCache;
import com.castle.drive.auth.service.AppLoginRedisService;
import com.castle.drive.auth.vo.AppLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/29 17:42
 * @Classname AppLoginUtil
 * @Description App登录工具类
 */
@Component
public class AppLoginUtil {

    private static AppLoginRedisService appLoginRedisService;

    public AppLoginUtil(AppLoginRedisService appLoginRedisService) {
        AppLoginUtil.appLoginRedisService = appLoginRedisService;
    }

    /**
     * 根据token从redis中获取登录用户信息
     * @param token
     * @return
     */
    public static AppLoginVo getLoginVo(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return appLoginRedisService.getLoginVo(token);
    }

    /**
     * 从当前线程中获取登录用户信息
     * @return
     */
    public static AppLoginVo getLoginVo() {
        return AppLoginCache.get();
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Long getUserId() {
        AppLoginVo appLoginVo = getLoginVo();
        if (Objects.nonNull(appLoginVo)) {
            return appLoginVo.getUserId();
        }
        return null;
    }

    /**
     * 获取登录用户ID字符串
     *
     * @return
     * @
     */
    public static String getUserIdString() {
        Long userId = getUserId();
        if (Objects.isNull(userId)) {
            return null;
        }
        return userId.toString();
    }

    /**
     * 获取登录用户名
     *
     * @return
     */
    public static String getUsername() {
        AppLoginVo appLoginVo = getLoginVo();
        if (Objects.nonNull(appLoginVo)) {
            return appLoginVo.getUsername();
        }
        return null;
    }

    /**
     * 获取用户角色ID
     *
     * @return
     */
    public static Long getUserRoleId() {
        AppLoginVo appLoginVo = getLoginVo();
        if (Objects.nonNull(appLoginVo)) {
            return appLoginVo.getUserRoleId();
        }
        return null;
    }

    /**
     * 获取用户角色编码
     *
     * @return
     */
    public static String getUserRoleCode() {
        AppLoginVo appLoginVo = getLoginVo();
        if (Objects.nonNull(appLoginVo)) {
            return appLoginVo.getUserRoleCode();
        }
        return null;
    }
}
