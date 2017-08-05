package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/22.
 */
public enum UserType {
    EMPLOYEE("内部员工"),
    ADMIN("管理员");
    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
