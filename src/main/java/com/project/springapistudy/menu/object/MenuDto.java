package com.project.springapistudy.menu.object;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuDto {

    private Long id;

    @NotBlank(message = MenuMessage.MENU_NAME_IS_NOT_NULL)
    private String menuName;

}
