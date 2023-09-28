package com.project.springapistudy.menu.domain;

import com.project.springapistudy.menu.object.MenuDto;
import com.project.springapistudy.menu.object.MenuVo;
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
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String menuName;

    private String useYN;

    public void updateBasicInfo(String menuName) {
        this.menuName = menuName;
    }

    public void remove() {
        this.useYN = "N";
    }
}
