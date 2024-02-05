package com.castle.drive.config.properties;

import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.util.YamlUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/30 10:19
 * @Classname LoginAppProperties
 * @Description 登录App属性配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.app")
public class LoginAppProperties {

    /**
     * 是否启动登录校验
     */
    private boolean enable;

    /**
     * 是否单次登录
     * true: 用户最后一次登录有效，之前的登录会话下线
     * false: 用户可多次登录，多次登录的会话都生效
     */
    private boolean singleLogin;

    /**
     * token过期天数
     */
    private Integer tokenExpireDays;

    /**
     * 方法是否鉴权
     */
    private boolean loginPermission;

    /**
     * 包含的路径
     */
    private List<String> includePaths;

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = YamlUtil.parseListArray(includePaths);
    }

    public Integer getTokenExpireDays() {
        if (Objects.isNull(tokenExpireDays)) {
            this.tokenExpireDays = LoginConstant.APP_TOKEN_EXPIRE_DAYS;
        }
        return tokenExpireDays;
    }

}
