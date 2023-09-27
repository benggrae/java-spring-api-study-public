package com.project.springapistudy.menu.object;

import com.project.springapistudy.menu.domain.Menu;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuVo {

    private Long id;

    private String menuName;

    public static MenuVo fromEntity(Menu menu) {
        return MenuVo.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .build();
    }

}
