package com.castle.drive.config;

import com.alibaba.excel.util.StringUtils;
import com.castle.drive.auth.interceptor.*;
import com.castle.drive.config.properties.*;
import com.castle.drive.framework.filter.JsonRequestBodyFilter;
import com.castle.drive.framework.filter.TraceIdLogFilter;
import com.castle.drive.framework.interceptor.PageHelperClearInterceptor;
import com.castle.drive.framework.xss.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/19 14:38
 * @Classname WebMvcConfig
 * @Description WebMvc配置
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private LoginProperties loginProperties;

    @Resource
    private LoginAdminProperties loginAdminProperties;

    @Resource
    private LoginAppProperties loginAppProperties;

    @Resource
    private LoginCommonProperties loginCommonProperties;

    @Resource
    private LocalFileProperties localFileProperties;

    @Resource
    private XssProperties xssProperties;

    @Resource
    private NotAuthProperties notAuthProperties;

    @Bean
    public ExcludePathInterceptor excludePathInterceptor() {
        return new ExcludePathInterceptor();
    }

    @Bean
    public NotAuthInterceptor notAuthInterceptor() {
        return new NotAuthInterceptor();
    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public AppLoginInterceptor loginAppInterceptor() {
        return new AppLoginInterceptor();
    }

    @Bean
    public CommonLoginInterceptor loginCommonInterceptor() {
        return new CommonLoginInterceptor();
    }

    @Bean
    public RefreshTokenInterceptor refreshTokenInterceptor() {
        return new RefreshTokenInterceptor();
    }

    @Bean
    public PageHelperClearInterceptor pageHelperClearInterceptor() {
        return new PageHelperClearInterceptor();
    }

    @Bean
    public FilterRegistrationBean<TraceIdLogFilter> traceIdLogFilter() {
        FilterRegistrationBean<TraceIdLogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        TraceIdLogFilter traceIdLogFilter = new TraceIdLogFilter();
        filterRegistrationBean.setFilter(traceIdLogFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<JsonRequestBodyFilter> jsonRequestBodyFilter() {
        FilterRegistrationBean<JsonRequestBodyFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        JsonRequestBodyFilter jsonRequestBodyFilter = new JsonRequestBodyFilter();
        filterRegistrationBean.setFilter(jsonRequestBodyFilter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }

    /**
     * XssFilter配置
     * @return
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setEnabled(xssProperties.isEnable());
        filterRegistrationBean.addUrlPatterns(xssProperties.getUrlPatterns());
        filterRegistrationBean.setOrder(xssProperties.getOrder());
        filterRegistrationBean.setAsyncSupported(xssProperties.isAsync());
        return filterRegistrationBean;
    }

    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        // 上传文件访问路径
        String accessPath = localFileProperties.getAccessPath();
        // 上传文件保存路径
        String uploadPath = localFileProperties.getUploadPath();
        if (StringUtils.isNotBlank(accessPath) && StringUtils.isNotBlank(uploadPath)) {
            // 虚拟目录文件映射
            registry.addResourceHandler(accessPath)
                    .addResourceLocations("file:" + uploadPath);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 加入的顺序就是拦截器执行的顺序
        registry.addInterceptor(excludePathInterceptor());
        // 没有权限的拦截器，直接返回无权限
        boolean enableNotAuth = notAuthProperties.isEnable();
        if (enableNotAuth) {
            List<String> includePaths = notAuthProperties.getIncludePaths();
            registry.addInterceptor(notAuthInterceptor()).addPathPatterns(includePaths);
        }

        // token拦截器
        registry.addInterceptor(tokenInterceptor()).excludePathPatterns("/admin/login", "/app/login");
        // 管理后台登录拦截器配置
        boolean enableAdminInterceptor = loginAdminProperties.isEnable();
        if (enableAdminInterceptor) {
            List<String> excludePaths = loginProperties.getExcludePaths();
            List<String> adminExcludePaths = loginAdminProperties.getExcludePaths();
            adminExcludePaths.addAll(excludePaths);
            registry.addInterceptor(loginInterceptor())
                    .addPathPatterns(loginAdminProperties.getIncludePaths())
                    .excludePathPatterns(adminExcludePaths);
        }
        // 用户端登录拦截器配置
        boolean enableAppInterceptor = loginAppProperties.isEnable();
        if (enableAppInterceptor) {
            List<String> appIncludePaths = loginAppProperties.getIncludePaths();
            registry.addInterceptor(loginAppInterceptor()).addPathPatterns(appIncludePaths);
        }
        // 刷新token拦截器
        registry.addInterceptor(refreshTokenInterceptor());

        // 系统公共请求拦截器，子拦截/common/开头的请求
        boolean enableCommonInterceptor = loginCommonProperties.isEnable();
        if (enableCommonInterceptor) {
            registry.addInterceptor(loginCommonInterceptor()).addPathPatterns(loginCommonProperties.getIncludePaths());
        }

        // 分页缓存清除拦截器
        registry.addInterceptor(pageHelperClearInterceptor());
    }

}
