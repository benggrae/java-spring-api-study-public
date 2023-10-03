package com.project.springapistudy.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BaseErrorCodeTest {

    @DisplayName("에러 코드 객체로 메시지를 생성해준다.")
    @Test
    void getFullErrorMessage() {
        //given
        enum ErrorCode implements com.project.springapistudy.global.domain.ErrorCode {
            ERROR("400", "에러입니당");
            private String code;
            private String message;

            ErrorCode(String code, String message) {
                this.code = code;
                this.message = message;
            }

            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return message;
            }

        }
        // then
        String message = ErrorCode.ERROR.getErrorMessage();
        // when
        assertThat(message)
                .contains(ErrorCode.ERROR.code, ErrorCode.ERROR.message);
    }

}
