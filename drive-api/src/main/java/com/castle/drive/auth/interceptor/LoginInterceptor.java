package com.castle.drive.auth.interceptor;

import com.castle.drive.auth.annotation.IgnoreLogin;
import com.castle.drive.auth.annotation.Permission;
import com.castle.drive.auth.cache.LoginCache;
import com.castle.drive.auth.util.LoginUtil;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.config.properties.LoginAdminProperties;
import com.castle.drive.framework.exception.LoginException;
import com.castle.drive.framework.exception.LoginTokenException;
import com.castle.drive.framework.interceptor.BaseExcludeMethodInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/4 17:06
 * @Classname LoginInterceptor
 * @Description 登录拦截器
 */
@Slf4j
public class LoginInterceptor extends BaseExcludeMethodInterceptor {

    @Resource
    private LoginAdminProperties loginAdminProperties;

    @Override
    protected boolean preHandleMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        // 获取token
        String token = TokenUtil.getToken();
        LoginVo loginVo = null;
        if (StringUtils.isNotBlank(token)) {
            // 获取登录用户信息
            loginVo = LoginUtil.getLoginVo(token);
            if (Objects.nonNull(loginVo)) {
                // 将管理后台的登录信息保存到当前线程中
                LoginCache.set(loginVo);
            }
        }
        // 判断是否存在@IgnoreLogin，存在，则跳过
        IgnoreLogin ignoreLoginAnnotation = getIgnoreAnnotation(handlerMethod);
        if (Objects.nonNull(ignoreLoginAnnotation)) {
            return true;
        }
        // 校验登录
        if (StringUtils.isBlank(token)) {
            throw new LoginTokenException("请登录后再操作");
        }
        // 校验登录用户信息
        if (Objects.isNull(loginVo)) {
            throw new LoginTokenException("登录已过期或登录信息不存在，请重新登录");
        }
        // 获取登录信息判断
        String roleCode = loginVo.getRoleCode();
        boolean loginPermission = loginAdminProperties.isLoginPermission();
        boolean admin = loginVo.isAdmin();
        if (!admin && loginPermission) {
            Permission permission = handlerMethod.getMethodAnnotation(Permission.class);
            if (Objects.nonNull(permission)) {
                // 从redis中获取权限列表
                List<String> permissions = loginVo.getPermissions();
                if (CollectionUtils.isEmpty(permissions)) {
                    throw new LoginException("当前用户未设置权限");
                }
                String value = permission.value();
                String role = permission.role();
                if (!permissions.contains(value)) {
                    log.error("没有访问权限的登录用户：" + loginVo);
                    throw new LoginException("没有访问权限");
                }
                if (StringUtils.isNotBlank(role)) {
                    if (Objects.equals(roleCode, role)) {
                        log.error("没有访问权限的登录用户：{}", loginVo);
                        throw new LoginException("该角色没有访问权限");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginCache.remove();
    }
}
