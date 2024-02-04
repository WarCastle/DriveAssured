package com.castle.drive.framework.interceptor;

import com.castle.drive.auth.annotation.Login;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/2 16:13
 * @Classname BaseMethodInterceptor
 * @Description 基础方法拦截器
 */
public abstract class BaseMethodInterceptor implements HandlerInterceptor {

    /**
     * 只处理方法的控制器
     * @param request
     * @param response
     * @param handlerMethod
     * @return
     * @throws Exception
     */
    protected abstract boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception;

    /**
     * 获取方法上和类上的@Login注解
     * @param handlerMethod
     * @return
     */
    protected Login getLoginAnnotation(HandlerMethod handlerMethod) {
        // 从方法上获取登录注解
        Login login = handlerMethod.getMethodAnnotation(Login.class);
        if (Objects.nonNull(login)) {
            return login;
        }
        // 从类上获取登录注解
        login = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Login.class);
        if (Objects.nonNull(login)) {
            return login;
        }
        return null;
    }

}
