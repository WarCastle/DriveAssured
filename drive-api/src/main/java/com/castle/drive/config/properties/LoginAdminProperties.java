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
 * @Date 2024/2/1 11:31
 * @Classname LoginAdminProperties
 * @Description 登录Admin配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "login.admin")
public class LoginAdminProperties {

    /**
     * 是否启用登录校验
     */
    private boolean enable;

    /**
     * 是否单次登录
     * true: 用户最后一次登录有效，之前的登录会话下线
     * false: 用户可多次登陆，多次登录的会话都生效
     */
    private boolean singleLogin;

    /**
     * token过期分钟数
     */
    private Integer tokenExpireMinutes;

    /**
     * 方法是否鉴权
     */
    private boolean loginPermission;

    /**
     * 包含的路径
     */
    private List<String> includePaths;

    /**
     * 排除的路径
     */
    private List<String> excludePaths;

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = YamlUtil.parseListArray(excludePaths);
    }

    public void setIncludePaths(List<String> includePaths) {
        this.includePaths = YamlUtil.parseListArray(includePaths);
    }

    public Integer getTokenExpireMinutes() {
        if (Objects.isNull(tokenExpireMinutes)) {
            this.tokenExpireMinutes = LoginConstant.ADMIN_TOKEN_EXPIRE_MINUTES;
        }
        return tokenExpireMinutes;
    }
}
