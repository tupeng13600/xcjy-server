package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/22.
 */
public enum CourseType {

    ONETOONE("一对一课程"),
    BOUTIQUEGROUP("精品小组"),
    NORMALGROUP("常规班");

    private String name;

    CourseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
