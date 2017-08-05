package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/8/5.
 */
public enum ApplicationStatusType {

    AUDITING("审核中"),
    AUDIT_FAIL("审核不通过"),
    AUDIT_SUCCESS("审核不通过");

    private String name;

    ApplicationStatusType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
