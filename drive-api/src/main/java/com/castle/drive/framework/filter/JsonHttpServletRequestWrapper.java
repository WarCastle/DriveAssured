package com.castle.drive.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author YuLong
 * @Date 2024/2/19 17:23
 * @Classname JsonHttpServletRequestWrapper
 * @Description Json Http Servlet请求包装器
 */
@Slf4j
public class JsonHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public JsonHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        // 保存一份InputStream，将其转换为字节数组
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("IOException：{}", e.getMessage());
        }
    }

    /**
     * 转换成string
     * @return
     */
    public String getBodyString() {
        return new String(body, StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 把保存好的InputStream，传下去
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }


}
