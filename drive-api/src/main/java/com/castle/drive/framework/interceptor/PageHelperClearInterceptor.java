package com.castle.drive.framework.interceptor;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author YuLong
 * @Date 2024/2/19 15:07
 * @Classname PageHelperClearInterceptor
 * @Description PageHelper清除拦截器
 */
@Slf4j
public class PageHelperClearInterceptor extends BaseMethodInterceptor {

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        try {
            PageHelper.clearPage();
        } catch (Exception e) {
            log.error("PageHelper clearPage失败：{}", e.getMessage());
        }
        return true;
    }
}
