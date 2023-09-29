package com.project.springapistudy.menu.domain;

import com.project.springapistudy.global.domain.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {
    MENU_IS_NOT_EMPTY_NAME("MENU001", "메뉴의 이름은 비어 있으면 안됩니다"),
    MENU_IS_NOT_EMPTY_CATEGORY_NAME("MENU002", "메뉴의 카테고리는 비어 있으면 안됩니다."),
    MENU_CATEGORY_IS_NOT_FOUND("MENU003", "메뉴 카테고리에 없는 메뉴 입니다."),
    MENU_NOT_FOUND("MENU004", "메뉴가 조회되지 않습니다."),
    MENU_NAME_IS_EXIST("MENU005", "메뉴이름이 이미 존재합니다.")
    ;

    private final String code;
    private final String message;

}
