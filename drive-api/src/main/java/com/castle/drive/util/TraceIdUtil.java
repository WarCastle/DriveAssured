package com.castle.drive.util;

import com.castle.drive.common.constant.CommonConstant;
import org.slf4j.MDC;

/**
 * @author YuLong
 * @Date 2024/2/5 15:50
 * @Classname TraceIdUtil
 * @Description 日志链路ID工具类
 */
public class TraceIdUtil {

    /**
     * 获取当前请求日志链路ID
     * @return
     */
    public static String getTraceId() {
        return MDC.get(CommonConstant.TRACE_ID);
    }
}
