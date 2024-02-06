package com.castle.drive.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/2/5 10:32
 * @Classname XssProperties
 * @Description Xss属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /**
     * 是否启用
     */
    private boolean enable;

    /**
     * 过滤的路径
     */
    private String[] urlPatterns;

    /**
     * 排序
     */
    private int order;

    /**
     * 是否支持异步
     */
    private boolean async;
}
