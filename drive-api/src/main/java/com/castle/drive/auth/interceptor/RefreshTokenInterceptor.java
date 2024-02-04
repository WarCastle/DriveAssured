package com.castle.drive.auth.interceptor;

import com.castle.drive.auth.service.AppLoginRedisService;
import com.castle.drive.auth.service.LoginRedisService;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.framework.interceptor.BaseExcludeMethodInterceptor;
import com.castle.drive.util.SystemTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/4 17:53
 * @Classname RefreshTokenInterceptor
 * @Description 刷新token拦截器
 */
@Slf4j
public class RefreshTokenInterceptor extends BaseExcludeMethodInterceptor {

    @Resource
    private LoginRedisService loginRedisService;

    @Resource
    private AppLoginRedisService appLoginRedisService;

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        SystemType systemType = SystemTypeUtil.getSystemTypeByPath(request);
        if (Objects.equals(SystemType.ADMIN, systemType)) {
            loginRedisService.refreshToken();
        } else if (Objects.equals(SystemType.APP, systemType)) {
            appLoginRedisService.refreshToken();
        }
        return true;
    }
}
