package com.castle.drive.common.constant;

import java.util.Collections;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/1/30 10:34
 * @Classname LoginConstant
 * @Description 登录常量类
 */
public interface LoginConstant {

    /**
     * 默认的token名称
     */
    String TOKEN_NAME = "Authorization";

    /**
     * token前缀
     */
    String TOKEN_PREFIX = "drive.";

    /**
     * 管理后台token前缀
     * drive.admin
     */
    String ADMIN_TOKEN_PREFIX = TOKEN_PREFIX + "admin.";

    /**
     * 管理后台token前缀
     * drive.app
     */
    String APP_TOKEN_PREFIX = TOKEN_PREFIX + "app.";

    /**
     * cookie.
     */
    String COOKIE_PREFIX = "cookie.";

    /**
     * 管理后台cookie token名称
     * drive.admin.cookie.token
     */
    String ADMIN_COOKIE_TOKEN_NAME = ADMIN_TOKEN_PREFIX + COOKIE_PREFIX + TOKEN_NAME;

    /**
     * 移动端cookie token名称
     * drive.app.cookie.token
     */
    String APP_COOKIE_TOKEN_NAME = APP_TOKEN_PREFIX + COOKIE_PREFIX + TOKEN_NAME;

    String ADMIN_URL_PREFIX = "/admin";

    String APP_URL_PREFIX = "/app";

    String COMMON_URL_PREFIX = "/common";

    /**
     * admin默认的token过期时间 60分钟
     */
    Integer ADMIN_TOKEN_EXPIRE_MINUTES = 60;

    /**
     * app默认的token过期时间 30天
     */
    Integer APP_TOKEN_EXPIRE_DAYS = 30;

    /**
     * 管理员角色编码
     */
    String ADMIN_ROLE_CODE = "admin";

    /**
     * 管理员admin角色ID
     */
    List<Long> ADMIN_ROLE_ID_LIST = Collections.singletonList(1L);

    /**
     * App端普通用户角色
     */
    Long APP_NORMAL_USER_ROLE = 1L;

}