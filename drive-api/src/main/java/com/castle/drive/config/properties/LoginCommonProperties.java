package com.castle.drive.config.properties;

import com.castle.drive.util.YamlUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/6 13:56
 * @Classname LoginCommonProperties
 * @Description 登录公共属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.common")
public class LoginCommonProperties {

    /**
     * 是否启用拦截器
     */
    private boolean enable;

    /**
     * 包含的路径
     */
    private List<String> includePaths;

    public void setExcludePaths(List<String> includePaths) {
        this.includePaths = YamlUtil.parseListArray(includePaths);
    }
}
