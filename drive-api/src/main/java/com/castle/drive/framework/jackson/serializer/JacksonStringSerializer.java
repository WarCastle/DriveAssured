package com.castle.drive.framework.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/6 15:37
 * @Classname JacksonStringSerializer
 * @Description Jackson字符串序列化器
 */
public class JacksonStringSerializer extends JsonSerializer<String> {

    public static final JacksonStringSerializer INSTANCE = new JacksonStringSerializer();

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.nonNull(s)) {
            // 去除字符串空格
            s = s.trim();
        }
        jsonGenerator.writeString(s);
    }
}
