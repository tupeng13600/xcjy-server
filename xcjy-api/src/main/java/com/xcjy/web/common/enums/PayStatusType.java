package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/25.
 */
public enum PayStatusType {
    YES("已经缴费"),
    NO("尚未缴费");

    private String name;

    PayStatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
