package com.castle.drive.auth.interceptor;

import com.castle.drive.auth.annotation.Login;
import com.castle.drive.auth.cache.AppLoginCache;
import com.castle.drive.auth.cache.LoginCache;
import com.castle.drive.auth.util.AppLoginUtil;
import com.castle.drive.auth.util.LoginUtil;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.framework.exception.LoginTokenException;
import com.castle.drive.framework.interceptor.BaseExcludeMethodInterceptor;
import com.castle.drive.util.SystemTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/4 16:14
 * @Classname CommonLoginInterceptor
 * @Description 公共登录拦截器
 */
@Slf4j
public class CommonLoginInterceptor extends BaseExcludeMethodInterceptor {

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        // 获取token
        String token = TokenUtil.getToken();
        SystemType systemType = null;
        if (StringUtils.isNotBlank(token)) {
            systemType = SystemTypeUtil.getSystemTypeByPath(token);
            if (Objects.equals(SystemType.ADMIN, systemType)) {
                // 获取管理后台登录用户信息
                LoginVo loginVo = LoginUtil.getLoginVo(token);
                if (Objects.nonNull(loginVo)) {
                    // 将管理后台的登录信息保存到当前线程中
                    LoginCache.set(loginVo);
                }
            } else if (Objects.equals(SystemType.APP, systemType)) {
                AppLoginVo loginVo = AppLoginUtil.getLoginVo(token);
                if (Objects.nonNull(loginVo)) {
                    // 将APP用户端的登录信息保存到当前线程中
                    AppLoginCache.set(loginVo);
                }
            }
        }
        // 如果不存在@Login注解，则跳过
        Login loginAnnotation = getLoginAnnotation(handlerMethod);
        if (Objects.isNull(loginAnnotation)) {
            return true;
        }
        if (StringUtils.isBlank(token)) {
            throw new LoginTokenException("请登录后再操作");
        }
        if (Objects.equals(SystemType.ADMIN, systemType)) {
            // 获取管理后台登录用户信息
            LoginVo loginVo = LoginCache.get();
            if (Objects.isNull(loginVo)) {
                throw new LoginTokenException("登录已过期或登录信息不存在，请重新登录");
            }
        } else if (Objects.equals(SystemType.APP, systemType)) {
            AppLoginVo loginVo = AppLoginCache.get();
            if (Objects.isNull(loginVo)) {
                throw new LoginTokenException("登录已过期或登录信息不存在，请重新登录");
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginCache.remove();
        AppLoginCache.remove();
    }
}