package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/25.
 */
public enum StudentPayType {
    STUDENTMANAGER_PAY("学管师续费"),
    COUNSELOR_PAY("咨询师缴费"),
    STUDENTMANAGER_BACK("学管师退费");

    private String name;

    StudentPayType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
