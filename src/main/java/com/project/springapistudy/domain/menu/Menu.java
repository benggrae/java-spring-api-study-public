package com.project.springapistudy.domain.menu;

import com.project.springapistudy.web.dto.MenuDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(unique = true)
    private String menuName;

    public MenuDto of() {
        return MenuDto.builder()
                .id(id)
                .menuName(menuName)
                .build();
    }

    public Menu updateBasicInfo(String menuName) {
        this.menuName = menuName;

        return this;
    }
}
