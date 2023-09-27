package com.project.springapistudy.common.exception;

public class DuplicationMenuException extends BaseException{

    @Override
    public String getMessage() {
        return "이미 등록된 메뉴입니다.";
    }

}
