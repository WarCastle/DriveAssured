package com.castle.drive.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YuLong
 * @Date 2024/1/31 18:19
 * @Classname WxMpProperties
 * @Description 微信小程序属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    /**
     * appid
     */
    private String appid;

    /**
     * 密钥
     */
    private String secret;
}
