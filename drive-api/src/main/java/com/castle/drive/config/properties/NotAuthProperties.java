package com.castle.drive.config.properties;

import com.castle.drive.util.YamlUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/6 14:30
 * @Classname NotAuthProperties
 * @Description 没有权限属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "not-auth")
public class NotAuthProperties {

    /**
     * 是否启用登录校验
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
