package com.xcjy.web.common.enums;

/**
 * Created by tupeng on 2017/7/25.
 */
public enum CounselorStudentStatusType {
    CONNECTION_NO("未联系"),
    NO_PAY("未缴费"),
    HAS_PAY("已缴费");

    private String name;

    CounselorStudentStatusType(String name) {
        this.name = name;
    }
}
