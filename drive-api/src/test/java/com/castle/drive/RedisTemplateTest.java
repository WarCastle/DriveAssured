package com.castle.drive;

import cn.hutool.json.JSONUtil;
import com.castle.drive.auth.vo.AppLoginVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author YuLong
 * @Date 2024/1/30 16:30
 * @Classname RedisTemplateTest
 * @Description RedisTemplate测试类
 */
@Slf4j
@SpringBootTest
public class RedisTemplateTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, User> redisTemplate;


    private static final TimeUnit TOKEN_TIME_UNIT = TimeUnit.DAYS;

    @Test
    void testSave() {
        int tokenExpireDays = 1;
        String loginTokenRedisKey = "1";
        AppLoginVo appLoginVo = new AppLoginVo();
        appLoginVo.setNickname("早起的虫儿被鸟吃");
        stringRedisTemplate.opsForValue().set(loginTokenRedisKey, JSONUtil.toJsonStr(appLoginVo), tokenExpireDays, TOKEN_TIME_UNIT);
    }

    @Test
    void testQuery() {
        String loginRedisKey = "1";
        String json = stringRedisTemplate.opsForValue().get(loginRedisKey);
        AppLoginVo appLoginVo = JSONUtil.toBean(json, AppLoginVo.class);
        log.info("{}", appLoginVo);
    }

    @Test
    void testSave2() {
        int tokenExpireDays = 1;
        String loginTokenRedisKey = "2";
        User user = new User();
        user.setName("晚起的鸟儿睡好觉");
        redisTemplate.opsForValue().set(loginTokenRedisKey, user, tokenExpireDays, TOKEN_TIME_UNIT);
    }

    @Test
    void testQuery2() {
        String loginRedisKey = "2";
        User user = redisTemplate.opsForValue().get(loginRedisKey);
        log.info("{}", user);
    }
}

@Data
class User implements Serializable {

    private static final long serialVersionUID = 1;

    private String name;
}