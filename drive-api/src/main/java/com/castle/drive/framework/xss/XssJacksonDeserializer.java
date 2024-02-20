package com.castle.drive.framework.xss;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * @author YuLong
 * @Date 2024/2/6 15:44
 * @Classname XssJacksonDeserializer
 * @Description Jackson请求参数字符串转义处理
 */
public class XssJacksonDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return StringEscapeUtils.escapeHtml4(jsonParser.getText());
    }
}
