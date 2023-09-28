package com.project.springapistudy.menu.object;

import com.project.springapistudy.menu.domain.Menu;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuDto {

    @NotBlank(message = MenuMessage.MENU_NAME_IS_NOT_NULL)
    private String menuName;

    public Menu toEntity() {
        return Menu.builder()
                .menuName(menuName)
                .useYN("Y")
                .build();
    }

}
