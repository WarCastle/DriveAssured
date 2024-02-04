package com.castle.drive.auth.interceptor;

import com.castle.drive.framework.exception.NotAuthException;
import com.castle.drive.framework.interceptor.BaseExcludeMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YuLong
 * @Date 2024/2/4 17:51
 * @Classname NotAuthInterceptor
 * @Description 没权限拦截器，项目演示用，生成项目根据情况而定
 */
@Slf4j
public class NotAuthInterceptor extends BaseExcludeMethodInterceptor {
    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        log.info("演示环境没有权限访问的url：" + request.getServletPath());
        throw new NotAuthException("演示环境不允许此操作");
    }
}
