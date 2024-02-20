package com.castle.drive.framework.exception;

import com.castle.drive.framework.response.ApiCode;
import com.castle.drive.framework.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/1/30 11:07
 * @Classname GlobalExceptionHandler
 * @Description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param exception
     * @return
     */
    public static <T> ApiResult<T> handle(Throwable exception) {
        if (exception instanceof LoginException) {
            return ApiResult.fail(ApiCode.TOKEN_EXCEPTION, exception.getMessage());
        } else if (exception instanceof NotAuthException) {
            return ApiResult.fail(exception.getMessage());
        } else if (exception instanceof BusinessException) {
            return ApiResult.fail(exception.getMessage());
        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = ex.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorMessage = fieldErrors.get(0).getDefaultMessage();
            log.error("参数校验错误：{}", errorMessage);
            return ApiResult.fail(errorMessage);
        } else if (exception instanceof HttpMessageNotReadableException) {
            return ApiResult.fail("请求参数解析异常");
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            return ApiResult.fail("请求参数数据类型错误");
        } else if (exception instanceof DuplicateKeyException) {
            return ApiResult.fail("数据违反唯一约束");
        } else if (exception instanceof DataIntegrityViolationException) {
            return ApiResult.fail("数据完整性异常");
        }
        return ApiResult.fail();
    }

    /**
     * 全局异常捕获
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<String> exceptionHandle(Exception exception) {
        log.error("异常exception：{}", exception.getMessage());
        return handle(exception);
    }
}
