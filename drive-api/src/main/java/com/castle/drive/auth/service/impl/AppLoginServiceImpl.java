package com.castle.drive.auth.service.impl;

import com.castle.drive.auth.dto.AppAccountLoginDto;
import com.castle.drive.auth.dto.AppLoginDto;
import com.castle.drive.auth.service.AppLoginRedisService;
import com.castle.drive.auth.service.AppLoginService;
import com.castle.drive.auth.util.AppLoginUtil;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.common.constant.LoginConstant;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.framework.exception.BusinessException;
import com.castle.drive.framework.exception.LoginException;
import com.castle.drive.user.entity.User;
import com.castle.drive.user.entity.UserRole;
import com.castle.drive.user.service.UserRoleService;
import com.castle.drive.user.service.UserService;
import com.castle.drive.util.IpRegionUtil;
import com.castle.drive.util.IpUtil;
import com.castle.drive.util.PasswordUtil;
import com.castle.drive.util.api.WxMpApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/1 15:27
 * @Classname AppLoginServiceImpl
 * @Description App登录服务接口实现类
 */
@Slf4j
@Service
public class AppLoginServiceImpl implements AppLoginService {

    @Resource
    private UserService userService;

    @Resource
    private AppLoginRedisService appLoginRedisService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public LoginTokenVo login(AppLoginDto dto) {
        String code = dto.getCode();
        // 获取微信openid
        String openid = WxMpApi.getOpenid(code);
        // 根据openid获取用户信息
        User user = userService.getUserByOpenid(openid);
        // 判断是否存在，不存在，则添加
        if (Objects.isNull(user)) {
            // 注册
            user = new User();
            // 用户昵称：用户+8位随机数字
            user.setNickname("用户" + RandomStringUtils.randomNumeric(8));
            // 设置微信小程序openid
            user.setOpenid(openid);
            // 设置注册时间
            user.setRegisterTime(new Date());
            String requestIp = IpUtil.getRequestIp();
            String ipAreaDesc = IpRegionUtil.getIpAreaDesc(requestIp);
            // 设置注册IP
            user.setRegisterIp(requestIp);
            // 设置注册IP区域
            user.setRegisterIpArea(ipAreaDesc);
            // 设置默认用户为普通角色
            user.setUserRoleId(LoginConstant.APP_NORMAL_USER_ROLE);
            boolean flag = userService.save(user);
            if (!flag) {
                throw new BusinessException("注册异常");
            }
            // 获取用户ID
            Long userId = user.getId();
            // 根据用户ID获取用户下信息
            user = userService.getById(userId);
        }
        return login(user);
    }

    @Override
    public LoginTokenVo accountLogin(AppAccountLoginDto dto) {
        String username = dto.getUsername();
        User user = userService.getUserByUsername(username);
        // 校验密码
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String password = dto.getPassword();
        String encryptPassword = PasswordUtil.encrypt(password, dbSalt);
        if (!encryptPassword.equals(dbPassword)) {
            throw new BusinessException("账号密码错误");
        }
        return login(user);
    }

    @Override
    public LoginTokenVo login(User user) {
        if (Objects.isNull(user)) {
            throw new LoginException("用户信息不存在");
        }
        Long userId = user.getId();
        // 生成token
        String token = TokenUtil.generateAppToken(userId);
        // 最后一次登录时间
        Date lastLoginTime = new Date();
        // 刷新登录信息
        refreshLoginInfo(user, token, lastLoginTime);
        // 设置登录信息
        String requestIp = IpUtil.getRequestIp();
        String ipAreaDesc = IpRegionUtil.getIpAreaDesc(requestIp);
        // 设置最后登录时间
        user.setLastLoginTime(lastLoginTime);
        // 设置最后登录IP
        user.setLastLoginIp(requestIp);
        // 设置最后登录IP区域
        user.setLastLoginIpArea(ipAreaDesc);
        userService.updateById(user);

        // TODO 添加用户登录日志

        // 返回token
        LoginTokenVo loginTokenVo = new LoginTokenVo();
        loginTokenVo.setToken(token);
        return loginTokenVo;
    }

    @Override
    public AppLoginVo refreshLoginInfo(User user, String token, Date lastLoginTime) {
        Long userId = user.getId();
        // 校验用户状态
        Boolean status = user.getStatus();
        if (!status) {
            throw new LoginException("用户已禁用");
        }
        // 设置登录用户对象
        AppLoginVo appLoginVo = new AppLoginVo();
        BeanUtils.copyProperties(user, appLoginVo);
        appLoginVo.setUserId(userId);
        appLoginVo.setLastLoginTime(lastLoginTime);
        // 系统类型 1：管理后台，2：用户端
        appLoginVo.setSystemType(SystemType.APP.getCode());
        Long userRoleId = user.getUserRoleId();
        appLoginVo.setUserRoleId(userRoleId);
        if (Objects.nonNull(userRoleId)) {
            UserRole userRole = userRoleService.getById(userRoleId);
            if (Objects.nonNull(userRole)) {
                appLoginVo.setUserRoleCode(userRole.getCode());
                appLoginVo.setUserRoleName(userRole.getName());
            }
        }
        // 保存登录信息到redis中
        appLoginRedisService.setLoginVo(token, appLoginVo);
        return appLoginVo;
    }

    @Override
    public AppLoginVo getLoginUserInfo() {
        AppLoginVo appLoginVo = AppLoginUtil.getLoginVo();
        if (Objects.isNull(appLoginVo)) {
            throw new LoginException("请先登录");
        }
        Long userId = appLoginVo.getUserId();
        User user = userService.getById(userId);
        // 刷新用户登录信息
        String token = TokenUtil.getToken();
        Date lastLoginTime = appLoginVo.getLastLoginTime();
        appLoginVo = refreshLoginInfo(user, token, lastLoginTime);
        return appLoginVo;
    }

    @Override
    public void logout() {
        // 获取token
        String token = TokenUtil.getToken();
        // 删除缓存
        appLoginRedisService.deleteLoginVo(token);
    }
}
