package com.project.springapistudy.util;

import lombok.Getter;

public class ResponseData {
    public static <T> ApiResult <T> success(T data, String msg) {
        return new ApiResult<>(data, msg);
    }

    @Getter
    public static class ApiResult<T> {
        T data;
        String msg;

        public ApiResult(T data, String msg) {
            this.data = data;
            this.msg = msg;
        }
    }
}
