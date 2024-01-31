package com.castle.drive.framework.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.castle.drive.common.constant.CommonConstant;
import com.castle.drive.framework.exception.ResponseException;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author YuLong
 * @Date 2024/1/30 13:46
 * @Classname ApiResult
 * @Description Rest Api 返回结果
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "响应结果")
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 7594052194764993562L;

    @Schema(description = "响应码 200：成功，500：失败")
    private int code;

    @Schema(description = "响应结果 true：成功，false：失败")
    private boolean success;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应结果数据")
    private T data;

    @Schema(description = "响应时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @Schema(description = "日志链路")
    private String traceId;

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(T data) {
        return result(ApiCode.SUCCESS, data);
    }

    public static <T> ApiResult<T> fail() {
        return fail(ApiCode.FAIL);
    }

    public static <T> ApiResult<T> fail(String message) {
        return fail(ApiCode.FAIL, message);
    }

    public static <T> ApiResult<T> fail(ApiCode apiCode) {
        return fail(apiCode, null);
    }

    public static <T> ApiResult<T> fail(ApiCode apiCode, String message) {
        if (ApiCode.SUCCESS == apiCode) {
            throw new ResponseException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
        }
        return result(apiCode, message, null);
    }

    public static <T> ApiResult<T> result(boolean flag) {
        if (flag) {
            return success();
        }
        return fail();
    }

    public static <T> ApiResult<T> result(ApiCode apiCode) {
        return result(apiCode, null);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, T data) {
        return result(apiCode, null, data);
    }

    public static <T> ApiResult<T> result(ApiCode apiCode, String message, T data) {
        if (Objects.isNull(apiCode)) {
            throw new ResponseException("结果状态码不能为空");
        }
        boolean success = false;
        int code = apiCode.getCode();
        if (ApiCode.SUCCESS.getCode() == code) {
            success = true;
        }
        String outMessage;
        if (StringUtils.isBlank(message)) {
            outMessage = apiCode.getMsg();
        } else {
            outMessage = message;
        }
        String traceId = MDC.get(CommonConstant.TRACE_ID);
        return ApiResult.<T>builder()
                .code(code)
                .success(success)
                .message(outMessage)
                .data(data)
                .time(new Date())
                .traceId(traceId)
                .build();
    }
}
