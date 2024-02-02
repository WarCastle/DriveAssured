package com.castle.drive.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/2/2 14:53
 * @Classname PasswordUtil
 * @Description 密码工具类
 */
public class PasswordUtil {

    private final static int MD5_PWD_LENGTH = 32;

    public static String encrypt(String password, String salt) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (StringUtils.isBlank(salt)) {
            salt = "";
        }
        // 如果密码长度不是32位，则进行md5加密
        if (Objects.equals(password.length(), MD5_PWD_LENGTH)) {
            password = DigestUtils.md5Hex(password);
        }
        // 将md5加密后的结果 + 盐，再进行sha256加密
        return DigestUtils.sha256Hex(password + salt);
    }
}
