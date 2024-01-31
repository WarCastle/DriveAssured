package com.castle.drive.util;

import com.alibaba.excel.util.StringUtils;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.common.enums.SystemType;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/31 14:08
 * @Classname SystemTypeUtil
 * @Description 系统类型工具类
 */
public class SystemTypeUtil {

    /**
     * 获取系统类型枚举
     * @param request
     * @return
     */
    public static SystemType getSystemTypeByPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        return getSystemTypeByPath(servletPath);
    }

    /**
     * 根据请求路径获取系统类型枚举
     * @param servletPath
     * @return
     */
    public static SystemType getSystemTypeByPath(String servletPath) {
        if (StringUtils.isBlank(servletPath)) {
            return null;
        }
        if (servletPath.startsWith(LoginConstant.ADMIN_URL_PREFIX)) {
            return SystemType.ADMIN;
        } else if (servletPath.startsWith(LoginConstant.APP_URL_PREFIX)) {
            return SystemType.APP;
        }
        return null;
    }

    /**
     * 判断token是哪个端
     * @param token
     * @return SystemType
     */
    public static SystemType getSystemTypeByToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        if (token.startsWith(LoginConstant.ADMIN_TOKEN_PREFIX)) {
            return SystemType.ADMIN;
        } else if (token.startsWith(LoginConstant.APP_TOKEN_PREFIX)) {
            return SystemType.APP;
        }
        return null;
    }

    /**
     * 判断token是哪个端
     * @return SystemType
     */
    public static SystemType getSystemTypeByToken() {
        String token = TokenUtil.getToken();
        return getSystemTypeByToken(token);
    }

    /**
     * 判断token是哪个端
     * @return systemType.getCode()
     */
    public static Integer getSystemTypeCodeByToken() {
        SystemType systemType = getSystemTypeByToken();
        if (Objects.nonNull(systemType)) {
            return systemType.getCode();
        }
        return null;
    }

    /**
     * 判断是否是管理后台系统
     * @return
     */
    public static boolean isAdminSystem() {
        SystemType systemType = getSystemTypeByToken();
        if (SystemType.ADMIN == systemType) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是App用户端系统
     * @return
     */
    public static boolean isAppSystem() {
        SystemType systemType = getSystemTypeByToken();
        if (SystemType.APP == systemType) {
            return true;
        }
        return false;
    }
}
