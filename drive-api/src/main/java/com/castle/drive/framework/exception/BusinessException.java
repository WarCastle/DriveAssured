package com.castle.drive.framework.exception;

/**
 * @author YuLong
 * @Date 2024/1/30 11:03
 * @Classname BusinessException
 * @Description 业务异常
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
