package com.project.springapistudy.common.handler;

import lombok.Getter;

public class ResponseData {

    public static ApiResult<?> fail(String message, String method, String uri, int status) {
        return new ApiResult<>(message, method, uri, status);
    }

    @Getter
    protected static class ApiResult<T> {
        String msg;
        String method;
        String uri;
        int status;

        public ApiResult(String msg, String method, String uri, int status) {
            this.msg = msg;
            this.method = method;
            this.uri = uri;
            this.status = status;
        }
    }
}
