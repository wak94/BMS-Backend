package com.wak.backend.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    // 成功时返回数据
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    // 成功但无数据
    public static <T> Result<T> success() {
        return success(null);
    }

    // 失败时返回错误信息
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    // 失败时使用枚举
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
