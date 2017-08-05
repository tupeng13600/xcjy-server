package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/8/5.
 */
public enum ProcessLogType {
    BACK_MONEY("退费申请"),
    CHANGE_SCHOOL("转校申请");

    private String name;

    ProcessLogType(String name) {
        this.name = name;
    }
}
