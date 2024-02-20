package com.castle.drive.framework.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/6 14:52
 * @Classname JacksonStringDeserializer
 * @Description Jackson字符串反序列化器
 */
public class JacksonStringDeserializer extends JsonDeserializer<String> {

    public static final JacksonStringDeserializer INSTANCE = new JacksonStringDeserializer();

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();
        if (Objects.nonNull(value)) {
            // 去除字符串空格
            value = value.trim();
        }
        return value;
    }
}
