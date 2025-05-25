package com.wak.backend.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return Result.error(400, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return Result.error(401, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error(e.getMessage());
        return Result.error(500, "服务器内部错误");
    }
}
