package com.castle.drive.auth.util;

import com.castle.drive.common.enums.SystemType;
import com.castle.drive.util.SystemTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/5 9:51
 * @Classname CommonLoginUtil
 * @Description 公共登录工具类
 */
@Slf4j
@Component
public class CommonLoginUtil {

    /**
     * 根据token获取用户ID
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        SystemType systemType = SystemTypeUtil.getSystemTypeByToken(token);
        return getUserId(systemType);
    }

    /**
     * 获取用户ID
     * @return
     */
    public static Long getUserId() {
        String token = TokenUtil.getToken();
        return getUserId(token);
    }

    /**
     * 获取登录用户ID字符串
     * @return
     */
    public static String getUserIdString() {
        Long userId = getUserId();
        if (Objects.isNull(userId)) {
            return null;
        }
        return userId.toString();
    }

    /**
     * 根据系统类型获取用户ID
     * @param systemType
     * @return
     */
    public static Long getUserId(SystemType systemType) {
        try {
            if (Objects.equals(SystemType.ADMIN, systemType)) {
                return LoginUtil.getUserId();
            } else if (Objects.equals(SystemType.APP, systemType)) {
                return AppLoginUtil.getUserId();
            }
        } catch (Exception e) {
            log.info("异常：{}", e.getMessage());
        }
        return null;
    }
}
