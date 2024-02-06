package com.castle.drive.config.properties;

import com.castle.drive.util.YamlUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/6 13:58
 * @Classname LoginProperties
 * @Description 登录属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "login")
public class LoginProperties {

    /**
     * 排除的路径
     */
    private List<String> excludePaths;

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = YamlUtil.parseListArray(excludePaths);
    }
}
