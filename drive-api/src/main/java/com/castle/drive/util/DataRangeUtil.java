package com.castle.drive.util;

import com.castle.drive.auth.util.AppLoginUtil;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.framework.query.DataRangeQuery;

/**
 * @author YuLong
 * @Date 2024/1/29 17:34
 * @Classname DataRangeUtil
 * @Description 数据范围工具类
 */
public class DataRangeUtil {

    /**
     * 设置Admin登录用户信息到查询参数中
     * @param dataRangeQuery
     */
    public static void handleAdminLogin(DataRangeQuery dataRangeQuery) {
        // LoginVo loginVo = LoginUtil.getLoginVo();
    }

    /**
     * 设置App登录用户信息到查询参数中
     * @param dataRangeQuery
     */
    public static void handleAppLogin(DataRangeQuery dataRangeQuery) {
        AppLoginVo appLoginVo = AppLoginUtil.getLoginVo();
        if (appLoginVo != null) {
            Long userId = appLoginVo.getUserId();
            dataRangeQuery.setLoginCommonUserId(userId);
            dataRangeQuery.setLoginAppUserId(userId);
            dataRangeQuery.setLoginAppUserRoleId(appLoginVo.getUserRoleId());
            dataRangeQuery.setLoginAppUserRoleCode(appLoginVo.getUserRoleCode());
        }
    }

    /**
     * 设置登录用户信息到查询参数中
     * @param dataRangeQuery
     */
    public static void handleCommonLogin(DataRangeQuery dataRangeQuery) {

    }
}
