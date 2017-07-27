package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/25.
 */
public enum StudentPayType {
    STUDENTMANAGER_PAY("学管师续费"),
    COUNSELOR_PAY("咨询师缴费"),
    SELF_PAY("学生自主缴费");

    private String name;

    StudentPayType(String name) {
        this.name = name;
    }
}
