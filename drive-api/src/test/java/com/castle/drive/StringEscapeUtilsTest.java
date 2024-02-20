package com.castle.drive;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author YuLong
 * @Date 2024/2/20 10:57
 * @Classname StringEscapeUtilsTest
 * @Description StringEscapeUtils测试类
 */
@SpringBootTest
public class StringEscapeUtilsTest {

    // Xss注入处理演示
    @Test
    public void test() {

        // 转义html脚本和反转义html脚本
        String inputText = "<input type=\"button\" value=\"点我\"/>";
        String s1 = StringEscapeUtils.escapeHtml4(inputText);

        System.out.println(s1);
        String s2 = StringEscapeUtils.unescapeHtml4(s1);
        System.out.println(s2+"\n");

        // 转义js脚本和反转义js脚本
        String s3 = StringEscapeUtils.escapeEcmaScript("<script>alert('点我')<script>");
        System.out.println(s3);
        String s4 = StringEscapeUtils.unescapeEcmaScript(s3);
        System.out.println(s4+"\n");

        // 把字符串转义为unicode编码和从把unicode编码转义为字符串
        String s5 = StringEscapeUtils.escapeJava("abc不要点我了");
        System.out.println(s5);
        String s6 = StringEscapeUtils.unescapeJava(s5);
        System.out.println(s6+"\n");

        // 转义XML和反转义XML
        String s7 = StringEscapeUtils.escapeXml11("<name>张三</name>");
        System.out.println(s7);
        String s8 = StringEscapeUtils.unescapeXml(s7);
        System.out.println(s8+"\n");
    }

}
