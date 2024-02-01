package com.castle.drive.framework.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/1 10:46
 * @Classname CustomStringRedisSerializer
 * @Description 自定义RedisKey前缀
 */
@Slf4j
public class CustomStringRedisSerializer extends StringRedisSerializer {

    private final String redisKeyPrefix;

    public CustomStringRedisSerializer(String redisKeyPrefix) {
        this.redisKeyPrefix = redisKeyPrefix;
        log.info("redisKeyPrefix: " + redisKeyPrefix);
    }

    @Override
    public byte[] serialize(String str) {
        if (StringUtils.isNotBlank(redisKeyPrefix) && Objects.nonNull(str) && !str.startsWith(redisKeyPrefix)) {
            str = redisKeyPrefix + "." + str;
        }
        return super.serialize(str);
    }

    @Override
    public String deserialize(byte[] bytes) {
        String str = super.deserialize(bytes);
        if (StringUtils.isNotBlank(redisKeyPrefix) && Objects.nonNull(str) && !str.startsWith(redisKeyPrefix)) {{
            str = redisKeyPrefix + "." + str;
        }}
        return str;
    }
}
