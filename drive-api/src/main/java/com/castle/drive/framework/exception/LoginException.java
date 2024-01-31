package com.castle.drive.framework.exception;

/**
 * @author YuLong
 * @Date 2024/1/30 11:00
 * @Classname LoginException
 * @Description 登录异常
 */
public class LoginException extends RuntimeException {

    public LoginException(String message) {
        super(message);
    }

}
