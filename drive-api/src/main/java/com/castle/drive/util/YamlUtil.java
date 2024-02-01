package com.castle.drive.util;

import com.castle.drive.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YuLong
 * @Date 2024/1/30 10:27
 * @Classname YamlUtil
 * @Description Yaml工具类
 */
public class YamlUtil {

    /**
     * 解析配置文件总的list行数据为数组
     * @param list
     * @return
     */
    public static List<String> parseListArray(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<String> parseList = new ArrayList<>();
        for (String string : list) {
            if (StringUtils.isBlank(string)) {
                continue;
            }
            if (string.contains(CommonConstant.COMMA)) {
                String[] array = string.split(CommonConstant.COMMA);
                parseList.addAll(Arrays.asList(array));
            } else {
                parseList.add(string);
            }
        }
        return parseList;
    }
}
