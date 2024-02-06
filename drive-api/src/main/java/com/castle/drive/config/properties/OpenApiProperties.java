package com.castle.drive.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/2/6 14:33
 * @Classname OpenApiProperties
 * @Description Swagger属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 团队地址
     */
    private String termsOfService;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人URL
     */
    private String contactUrl;

    /**
     * 联系人Email
     */
    private String contactEmail;

    /**
     * 版本
     */
    private String version;

    /**
     * 拓展描述
     */
    private String externalDescription;

    /**
     * 拓展Url
     */
    private String externalUrl;

}
