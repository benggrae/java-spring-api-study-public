package com.project.springapistudy.util;

import lombok.Getter;

public class ResponseData {
    public static <T> ApiResult <T> success(T data, String msg) {
        return new ApiResult<>(data, msg);
    }
    public static <T> ApiResult <T> success(String msg) {
        return new ApiResult<>(msg);
    }

    public static ApiResult<?> fail(String message) {
        return new ApiResult<>(message);
    }

    @Getter
    public static class ApiResult<T> {
        T data;
        String msg;

        public ApiResult(T data, String msg) {
            this.data = data;
            this.msg = msg;
        }

        public ApiResult(String msg) {
            this.msg = msg;
        }
    }
}
