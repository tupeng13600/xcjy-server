package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/8/5.
 */
public enum HandlerStatusType {

    WAIT_AUDIT("待审核"),
    AUDIT_FAIL("审核不通过"),
    AUDIT_SUCCESS("审核不通过");

    private String name;

    HandlerStatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
