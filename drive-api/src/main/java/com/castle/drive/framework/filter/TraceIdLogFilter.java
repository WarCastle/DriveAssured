package com.castle.drive.framework.filter;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.castle.drive.common.constant.CommonConstant;
import com.castle.drive.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author YuLong
 * @Date 2024/2/19 16:38
 * @Classname TraceIdLogFilter
 * @Description 跟踪Id日志过滤器
 */
@Slf4j
public class TraceIdLogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            // 设置日志链路ID
            String traceId = IdWorker.getIdStr();
            // 设置请求IP
            String ip = IpUtil.getRequestIp(request);
            try {
                MDC.put(CommonConstant.TRACE_ID, traceId);
                MDC.put(CommonConstant.IP, ip);
            } catch (IllegalArgumentException e) {
                log.error("IllegalArgumentException：{}", e.getMessage());
            }
            // 执行
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            // 移除日志链路ID
            try {
                MDC.remove(CommonConstant.TRACE_ID);
            } catch (IllegalArgumentException e) {
                log.error("移除日志链路ID失败：{}", e.getMessage());
            }
        }
    }
}
