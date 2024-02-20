package com.castle.drive.framework.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * @author YuLong
 * @Date 2024/2/6 15:41
 * @Classname XssJacksonSerializer
 * @Description Jackson响应参数字符串转义处理
 */
@Slf4j
public class XssJacksonSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(s));
    }
}
