package com.castle.drive.auth.util;

import com.castle.drive.auth.cache.LoginCache;
import com.castle.drive.auth.service.LoginRedisService;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.framework.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/30 10:15
 * @Classname LoginUtil
 * @Description 登录工具类
 */
@Component
public class LoginUtil {
    private static LoginRedisService loginRedisService;

    public LoginUtil(LoginRedisService loginRedisService) {
        LoginUtil.loginRedisService = loginRedisService;
    }

    /**
     * 根据token从redis中获取登录用户信息
     *
     * @param token
     * @return
     * @
     */
    public static LoginVo getLoginVo(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return loginRedisService.getLoginVo(token);
    }

    /**
     * 从当前线程中获取登录用户信息
     *
     * @return
     */
    public static LoginVo getLoginVo() {
        return LoginCache.get();
    }

    /**
     * 获取登录信息
     *
     * @return
     */
    public static List<String> getPermissions() {
        LoginVo loginVo = getLoginVo();
        if (Objects.nonNull(loginVo)) {
            return loginVo.getPermissions();
        }
        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Long getUserId() {
        LoginVo loginVo = getLoginVo();
        if (Objects.nonNull(loginVo)) {
            return loginVo.getUserId();
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
        LoginVo loginVo = getLoginVo();
        if (Objects.nonNull(loginVo)) {
            return loginVo.getUsername();
        }
        return null;
    }

    /**
     * 获取登录角色ID
     *
     * @return
     */
    public static Long getRoleId() {
        LoginVo loginVo = getLoginVo();
        if (Objects.nonNull(loginVo)) {
            return loginVo.getRoleId();
        }
        return null;
    }

    /**
     * 判断是否是管理员
     *
     * @return
     * @
     */
    public static boolean isAdmin() {
        LoginVo loginVo = getLoginVo();
        if (Objects.nonNull(loginVo)) {
            return loginVo.isAdmin();
        }
        return false;
    }

    /**
     * 判断不是管理员
     *
     * @return
     * @
     */
    public static boolean isNotAdmin() {
        return !isAdmin();
    }

    /**
     * 检查是否是管理员
     */
    public static void checkAdmin() {
        boolean admin = isAdmin();
        if (!admin) {
            throw new BusinessException("不是管理员，无权限");
        }
    }

}
