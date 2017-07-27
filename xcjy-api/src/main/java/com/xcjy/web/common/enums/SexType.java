package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/23.
 */
public enum SexType {
    MALE("男"),FEMALE("女");

    private String name;

    SexType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
