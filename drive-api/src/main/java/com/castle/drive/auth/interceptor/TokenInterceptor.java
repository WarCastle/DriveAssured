package com.castle.drive.auth.interceptor;

import com.castle.drive.auth.cache.TokenCache;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.framework.interceptor.BaseExcludeMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YuLong
 * @Date 2024/2/4 18:00
 * @Classname TokenInterceptor
 * @Description Token拦截器
 */
@Slf4j
public class TokenInterceptor extends BaseExcludeMethodInterceptor {

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        // 获取token
        String token = TokenUtil.getToken();
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 设置token值到当前线程中，避免重复获取
        TokenCache.set(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenCache.remove();
    }
}
