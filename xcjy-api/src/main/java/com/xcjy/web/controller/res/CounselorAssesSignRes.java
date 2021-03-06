package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class CounselorAssesSignRes {

    private String employeeId;

    private String schoolId;

    private String schoolName;

    private String name;

    private String phone;

    private Integer totalStudentNum = 0;

    private Integer totalMoney = 0;

    private Integer signNum = 0;

    private Integer totalBack = 0;

}
