package com.castle.drive.util;

import com.castle.drive.common.constant.CommonConstant;
import com.castle.drive.common.constant.RequestHeadConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/31 15:45
 * @Classname HttpServletRequestUtil
 * @Description 获取当前请求的HttpServletRequest对象
 */
public class HttpServletRequestUtil {

    /**
     * 获取HttpServletRequest对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return  ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 判断是否是Knife4或者是Swagger接口文档访问的请求
     * @return
     */
    public static boolean isDocRequest() {
        return isDocRequest(getRequest());
    }

    /**
     * 判断是否是Knife4或者是Swagger接口文档访问的请求
     * @param request
     * @return
     */
    public static boolean isDocRequest(HttpServletRequest request) {
        String requestOrigin = request.getHeader(RequestHeadConstant.REQUEST_ORIGIN);
        String referer = request.getHeader(RequestHeadConstant.REFERER);
        boolean docRequest = false;
        if (Objects.equals(CommonConstant.KNIFE4J, requestOrigin)) {
            docRequest = true;
        } else if (StringUtils.isNotBlank(referer) && referer.contains(CommonConstant.SWAGGER_UI_PATH)) {
            docRequest =  true;
        }
        return docRequest;
    }
}
