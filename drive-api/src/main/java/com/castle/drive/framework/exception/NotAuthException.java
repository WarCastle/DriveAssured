package com.castle.drive.framework.exception;

/**
 * @author YuLong
 * @Date 2024/1/30 13:43
 * @Classname NotAuthException
 * @Description 没有权限异常
 */
public class NotAuthException extends BusinessException {

    public NotAuthException(String message) {
        super(message);
    }

}
