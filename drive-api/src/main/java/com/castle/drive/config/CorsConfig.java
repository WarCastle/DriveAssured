package com.castle.drive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author YuLong
 * @Date 2024/2/5 10:18
 * @Classname CorsConfig
 * @Description Cors配置
 */
@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许访问的源
        corsConfiguration.addAllowedOriginPattern("*");
        // 允许访问的请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许访问的请求方式
        corsConfiguration.setAllowedMethods(Arrays.asList("OPTION", "GET", "POST"));
        // 是否允许发送cookie
        corsConfiguration.setAllowCredentials(true);
        // 允许响应的头
        corsConfiguration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(configurationSource);
    }
}
