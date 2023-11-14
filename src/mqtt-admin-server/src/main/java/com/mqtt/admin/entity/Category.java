package com.mqtt.admin.entity;

import lombok.Getter;

@Getter
public enum Category {
    DEFAULT(0);

    private final int value;

    Category(int value) {
        this.value = value;
    }
}
