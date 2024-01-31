package com.castle.drive.auth.util;

import com.alibaba.excel.util.StringUtils;
import com.castle.drive.auth.cache.TokenCache;
import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.framework.exception.LoginTokenException;
import com.castle.drive.util.CookieUtil;
import com.castle.drive.util.HttpServletRequestUtil;
import com.castle.drive.util.SystemTypeUtil;
import com.castle.drive.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/30 17:41
 * @Classname TokenUtil
 * @Description Token工具类
 */
@Slf4j
@Component
public class TokenUtil {

    /**
     * 生成Admin后台Token
     * @param userId
     * @return
     */
    public static String generateAdminToken(Long userId) {
        String userMd5 = DigestUtils.md5Hex(userId.toString());
        return LoginConstant.ADMIN_TOKEN_PREFIX + userMd5 + "." + UUIDUtil.getUuid();
    }

    /**
     * 生成用户端Token
     * @param userId
     * @return
     */
    public static String generateAppToken(Long userId) {
        String userMd5 = DigestUtils.md5Hex(userId.toString());
        return LoginConstant.APP_TOKEN_PREFIX + userMd5 + "." + UUIDUtil.getUuid();
    }

    /**
     * 获取短的ID
     * @param userId
     * @return
     */
    public static String getShortId(Long userId) {
        // 将数字转换成数字加字母变为更短的字符串
        // 36表示基数(10位数字 + 26个字符)
        return Long.toString(userId, 36);
    }

    /**
     * 解析短的ID
     * @param shortUserId
     * @return
     */
    public static Long parseShortId(String shortUserId) {
        return Long.parseLong(shortUserId, 36);
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        // 从当前线程获取
        return TokenCache.get();
    }

    /**
     * 从请求头或者请求参数中
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求头中获取token
        String token = request.getHeader(LoginConstant.TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            // 从请求参数中获取token
            token = request.getParameter(LoginConstant.TOKEN_NAME);
        }
        String servletPath = request.getServletPath();
        SystemType systemType = SystemTypeUtil.getSystemTypeByPath(servletPath);
        if (StringUtils.isBlank(token)) {
            // 从cookie中获取token
            token = getTokenByCookie(request, systemType);
            if (StringUtils.isNotBlank(token) && !token.startsWith(LoginConstant.TOKEN_PREFIX)) {
                token = null;
            }
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        // 校验token
        if (!token.startsWith(LoginConstant.TOKEN_PREFIX)) {
            log.error("token错误：" + token);
            throw new LoginTokenException("token错误");
        }
        if (SystemType.ADMIN == systemType) {

        }
        return null;
    }

    /**
     * 从Cookie中获取token
     * @param request
     * @param systemType
     * @return
     */
    public static String getTokenByCookie(HttpServletRequest request, SystemType systemType) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return null;
        }
        if (SystemType.ADMIN == systemType) {
            // 管理系统token的cookie可以通过接口文档传递或者浏览器页面传递
            return CookieUtil.getCookieValueByName(cookies, LoginConstant.ADMIN_COOKIE_TOKEN_NAME);
        } else if (SystemType.APP == systemType) {
            // 判断是否是接口文档请求，是则从cookie中获取，否则不获取，app接口只能通过接口文档传递token的cookie
            if (HttpServletRequestUtil.isDocRequest()) {
                return CookieUtil.getCookieValueByName(cookies, LoginConstant.APP_COOKIE_TOKEN_NAME);
            }
            return null;
        } else {
            String cookieValue = CookieUtil.getCookieValueByName(cookies, LoginConstant.ADMIN_COOKIE_TOKEN_NAME);
            if (StringUtils.isBlank(cookieValue)) {
                if (HttpServletRequestUtil.isDocRequest()) {
                    cookieValue = CookieUtil.getCookieValueByName(cookies, LoginConstant.APP_COOKIE_TOKEN_NAME);
                }
            }
            return cookieValue;
        }
    }

    /**
     * 校验是否是admin的token
     * @param token
     */
    public static void checkAdminToken(String token) {
        SystemType systemType = SystemTypeUtil.getSystemTypeByToken(token);
        if (SystemType.ADMIN != systemType) {
            throw new LoginTokenException("非管理后台token");
        }
    }

    public static void checkAppToken(String token) {
        SystemType systemType = SystemTypeUtil.getSystemTypeByToken(token);
        if (SystemType.APP != systemType) {
            throw new LoginTokenException("非客户端token");
        }
    }

}
