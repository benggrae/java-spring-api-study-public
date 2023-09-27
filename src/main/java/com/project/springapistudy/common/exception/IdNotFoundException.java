package com.project.springapistudy.common.exception;

public class IdNotFoundException extends BaseException {

    @Override
    public String getMessage() {
        return "조회된 데이터가 없습니다.";
    }

}
