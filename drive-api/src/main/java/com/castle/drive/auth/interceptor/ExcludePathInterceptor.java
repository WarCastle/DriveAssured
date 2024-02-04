package com.castle.drive.auth.interceptor;

import com.castle.drive.common.constant.CommonConstant;
import com.castle.drive.config.properties.DriveProperties;
import com.castle.drive.framework.interceptor.BaseMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/4 16:35
 * @Classname ExcludePathInterceptor
 * @Description 排除路径拦截器
 */
@Slf4j
public class ExcludePathInterceptor extends BaseMethodInterceptor {

    @Resource
    private DriveProperties driveProperties;

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        String servletPath = request.getServletPath();
        List<String> excludePaths = driveProperties.getExcludePaths();
        if (CollectionUtils.isNotEmpty(excludePaths)) {
            for (String excludePath : excludePaths) {
                AntPathMatcher antPathMatcher = new AntPathMatcher();
                boolean match = antPathMatcher.match(excludePath, servletPath);
                if (match) {
                    request.setAttribute(CommonConstant.REQUEST_PARAM_EXCLUDE_PATH, true);
                    break;
                }
            }
        }
        return true;
    }
}
