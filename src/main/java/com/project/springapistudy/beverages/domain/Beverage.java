package com.project.springapistudy.beverages.domain;

import com.project.springapistudy.commons.domain.BaseTime;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Beverage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("제조음료 식별자")
    private long id;

    @Column(nullable = false, unique = true)
    @Comment("제조음료 이름")
    private String name;

    @Column(nullable = false)
    @Comment("제조음료 가격")
    private long price;

    @Column(nullable = false)
    @Comment("제조음료 삭제여부")
    private boolean deleted;

    public Beverage(String name, Long price) {
        this.name = name;
        this.price = price;
        this.deleted = false;
    }

    public Beverage() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public void update(Beverage beverage) {
        this.name = beverage.getName();
        this.price = beverage.getPrice();
    }

    public void delete() {
        this.deleted = true;
    }
}
