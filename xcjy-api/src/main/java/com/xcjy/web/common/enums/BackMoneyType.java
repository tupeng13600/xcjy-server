package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/9/9.
 */
public enum BackMoneyType {

    COUNSELOR("咨询师退费"),
    STMANAGER("学管师退费");

    private String name;

    BackMoneyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
