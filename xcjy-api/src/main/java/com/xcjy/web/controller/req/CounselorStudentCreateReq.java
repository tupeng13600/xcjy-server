package com.xcjy.web.controller.req;

import lombok.Data;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/25.
 */
@Data
public class CounselorStudentCreateReq {

    private String schoolId;

    private String employeeId;

    private Set<String> studentId;

}
