package com.castle.drive.framework.filter;

import com.alibaba.excel.util.StringUtils;
import com.castle.drive.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/19 17:17
 * @Classname JsonRequestBodyFilter
 * @Description JsonRequestBody过滤器
 */
@Slf4j
public class JsonRequestBodyFilter implements Filter {

    private static final String APPLICATION_JSON = "application/json";
    private static final String METHOD_POST = "POST";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        JsonHttpServletRequestWrapper requestWrapper = null;
        try {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                String method = httpServletRequest.getMethod();
                String contentType = httpServletRequest.getContentType();
                if (METHOD_POST.equalsIgnoreCase(method) && StringUtils.isNotBlank(contentType)) {
                    contentType = contentType.toLowerCase();
                    if (contentType.startsWith(APPLICATION_JSON)) {
                        requestWrapper = new JsonHttpServletRequestWrapper((HttpServletRequest) request);
                        String bodyString = requestWrapper.getBodyString();
                        requestWrapper.setAttribute(CommonConstant.REQUEST_PARAM_BODY_STRING, bodyString);
                    }
                }
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        if (Objects.isNull(requestWrapper)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }
}
