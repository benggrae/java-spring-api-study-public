package com.project.springapistudy.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude
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
