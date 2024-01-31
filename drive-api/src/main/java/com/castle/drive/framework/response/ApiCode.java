package com.castle.drive.framework.response;

import lombok.Getter;

/**
 * @author YuLong
 * @Date 2024/1/30 13:56
 * @Classname ApiCode
 * @Description Rest Api 响应码
 */
@Getter
public enum ApiCode {

    /**
     * 处理成功
     */
    SUCCESS(200, "处理成功"),

    /**
     * 处理失败
     */
    FAIL(500, "处理失败"),

    /**
     * token已过期
     */
    TOKEN_EXCEPTION(5001, "token为空或已过期，请重新登录");

    private final int code;

    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] apiCodes = ApiCode.values();
        for (ApiCode apiCode : apiCodes) {
            if (apiCode.getCode() == code) {
                return apiCode;
            }
        }
        return FAIL;
    }

}
