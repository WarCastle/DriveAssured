package com.castle.drive.auth.service.impl;

import com.castle.drive.auth.service.AppLoginRedisService;
import com.castle.drive.auth.vo.AppLoginVo;
import com.castle.drive.common.constant.RedisKey;
import com.castle.drive.config.properties.LoginAppProperties;
import com.castle.drive.framework.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author YuLong
 * @Date 2024/1/29 17:46
 * @Classname AppLoginRedisServiceImpl
 * @Description App登录Redis服务实现类
 */
@Slf4j
@Service
public class AppLoginRedisServiceImpl implements AppLoginRedisService {

    private static final TimeUnit TOKEN_TIME_UNIT = TimeUnit.DAYS;

    @Resource
    private LoginAppProperties loginAppProperties;

    @Resource
    private RedisTemplate<String, AppLoginVo> redisTemplate;

    private Integer tokenExpireDays;

    @PostConstruct
    public void init() {
        log.info("loginAppProperties = " + loginAppProperties);
        tokenExpireDays = loginAppProperties.getTokenExpireDays();
    }

    @Override
    public String getLoginRedisKey(String token) {
        return String.format(RedisKey.LOGIN_TOKEN, token);
    }

    @Override
    public void setLoginVo(String token, AppLoginVo appLoginVo) {
        if (Objects.isNull(appLoginVo)) {
            throw new LoginException("登录用户信息不能为空");
        }
        if (loginAppProperties.isSingleLogin()) {
            // 单点登录，则删除之前该用户的key
            deleteLoginInfoByToken(token);
        }
        // 用户信息
        String loginTokenRedisKey = getLoginRedisKey(token);
        redisTemplate.opsForValue().set(loginTokenRedisKey, appLoginVo, tokenExpireDays, TOKEN_TIME_UNIT);
    }

    @Override
    public AppLoginVo getLoginVo(String token) {
        if (StringUtils.isBlank(token)) {
            throw new LoginException("token不能为空");
        }
        String loginRedisKey = getLoginRedisKey(token);
        return redisTemplate.opsForValue().get(loginRedisKey);
    }

    @Override
    public void deleteLoginVo(String token) {
        if (StringUtils.isBlank(token)) {
            throw new LoginException("token不能为空");
        }
        String loginRedisKey = getLoginRedisKey(token);
        redisTemplate.delete(loginRedisKey);
    }

    @Override
    public void refreshToken() {
        // 刷新token

    }

    @Override
    public void deleteLoginInfoByToken(String token) {

    }
}
