package com.project.springapistudy.menu.domain;

import com.project.springapistudy.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {
    MENU_IS_NOT_EMPTY_NAME("MENU001", "메뉴의 이름은 비어 있으면 안됩니다"),
    MENU_IS_NOT_EMPTY_CATEGORY_NAME("MENU002", "메뉴의 카테고리는 비어 있으면 안됩니다.");

    private final String code;
    private final String message;

}
