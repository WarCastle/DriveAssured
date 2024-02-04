package com.castle.drive.auth.service.impl;

import com.castle.drive.auth.dto.LoginDto;
import com.castle.drive.auth.service.LoginRedisService;
import com.castle.drive.auth.service.LoginService;
import com.castle.drive.auth.util.LoginUtil;
import com.castle.drive.auth.util.TokenUtil;
import com.castle.drive.auth.vo.LoginTokenVo;
import com.castle.drive.auth.vo.LoginVo;
import com.castle.drive.common.enums.SystemType;
import com.castle.drive.framework.exception.BusinessException;
import com.castle.drive.framework.exception.LoginException;
import com.castle.drive.system.entity.SysRole;
import com.castle.drive.system.entity.SysUser;
import com.castle.drive.system.mapper.SysMenuMapper;
import com.castle.drive.system.mapper.SysRoleMapper;
import com.castle.drive.system.mapper.SysUserMapper;
import com.castle.drive.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/2 16:45
 * @Classname LoginServiceImpl
 * @Description 登录服务接口实现类
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private LoginRedisService loginRedisService;


    @Override
    public LoginTokenVo login(LoginDto dto) {
        String username = dto.getUsername();
        SysUser sysUser = sysUserMapper.getSysUserByUsername(username);
        if (sysUser == null) {
            throw new BusinessException("用户信息不存在");
        }
        // 用户ID
        Long userId = sysUser.getId();
        // 校验密码
        String dbPassword = sysUser.getPassword();
        String dbSalt = sysUser.getSalt();
        String password = dto.getPassword();
        String encryptPassword = PasswordUtil.encrypt(password, dbSalt);
        if (!encryptPassword.equals(dbPassword)) {
            throw new BusinessException("账号密码错误");
        }
        // 生成token
        String token = TokenUtil.generateAdminToken(userId);
        // 刷新登录信息
        refreshLoginInfo(sysUser, token, new Date());
        // 返回token
        LoginTokenVo loginTokenVo = new LoginTokenVo();
        loginTokenVo.setToken(token);
        return loginTokenVo;
    }

    @Override
    public LoginVo refreshLoginInfo(SysUser sysUser, String token, Date loginTime) {
        // 用户ID
        Long userId = sysUser.getId();
        // 校验用户状态
        Boolean status = sysUser.getStatus();
        if (status == false) {
            throw new BusinessException("用户已禁用");
        }
        // 查询用户角色
        Long roleId = sysUser.getRoleId();
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (sysRole == null) {
            throw new BusinessException("该用户不存在可用的角色");
        }
        // 设置登录用户对象
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(sysUser, loginVo);
        loginVo.setUserId(userId);
        loginVo.setLoginTime(loginTime);
        loginVo.setAdmin(sysUser.getIsAdmin());
        loginVo.setRoleCode(sysRole.getCode());
        loginVo.setRoleName(sysRole.getName());
        // 系统类型 1：管理后台，2：用户端
        loginVo.setSystemType(SystemType.ADMIN.getCode());
        // 获取登录用户的权限编码
        List<String> permissions = sysMenuMapper.getPermissionCodesByUserId(userId);
        loginVo.setPermissions(permissions);
        // 保存登录信息到redis中
        loginRedisService.setLoginVo(token, loginVo);
        return loginVo;
    }

    @Override
    public LoginVo getLoginUserInfo() {
        LoginVo loginVo = LoginUtil.getLoginVo();
        if (loginVo == null) {
            throw new LoginException("请先登录");
        }
        // 根据用户ID获取用户信息
        Long userId = loginVo.getUserId();
        // 获取用户登录时间
        Date loginTime = loginVo.getLoginTime();
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException("用户信息不存在");
        }
        // 刷新登录信息
        String token = TokenUtil.getToken();
        loginVo = refreshLoginInfo(sysUser, token, loginTime);
        return loginVo;
    }

    @Override
    public void logout() {
        try {
            // 获取token
            String token = TokenUtil.getToken();
            // 删除缓存
            loginRedisService.deleteLoginVo(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

