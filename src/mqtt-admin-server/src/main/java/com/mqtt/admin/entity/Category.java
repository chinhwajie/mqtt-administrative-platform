package com.mqtt.admin.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Category {
    DEFAULT(0),
    MOBILE(1);

    private final int value;

    Category(int value) {
        this.value = value;
    }

    public static Category get(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "mobile":
                return Category.MOBILE;
            case "default":
                return Category.DEFAULT;
            default:
                return Category.DEFAULT;
        }
    }

    public static List<Category> getAll() {
        return List.of(Category.values());
    }
}
