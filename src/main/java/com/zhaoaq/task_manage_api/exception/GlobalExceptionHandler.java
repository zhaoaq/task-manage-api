package com.zhaoaq.task_manage_api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // 打印错误日志，方便我们排查问题
        logger.error("Resource not found: {}", ex.getMessage());

        // 创建我们自定义的ErrorResponse对象
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // 404
                "您请求的资源未找到", // 给用户看的信息
                ex.getMessage() // 原始的、详细的异常信息

        );

        // 返回一个ResponseEntity，可以精细控制HTTP状态码和响应体
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 处理IllegalArgumentException (例如，Controller中手动校验参数失败时抛出)
    @ExceptionHandler(IllegalAccessError.class)
    public  ResponseEntity<ErrorResponse> handleIllegalAccessException(IllegalAccessException ex, WebRequest request) {

        logger.warn("Illegal Argument: {}", ex.getMessage(),ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "请求参数无效",
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 通用异常处理器，处理所有其他未被专门处理的异常
    // 确保这个处理器在最后，或者其处理的异常类型是最泛化的 (Exception.class)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {

        logger.error("An unexpected error occured: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "服务器内部错误，请稍后重试",
                ex.getClass().getSimpleName() + ": " + ex.getMessage() // 可以选择不暴露具体异常类型给客户端
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
