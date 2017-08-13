package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.StudentPayType;
import lombok.Data;

/**
 * Created by tupeng on 2017/8/13.
 */
@Data
public class StudentPayLogDetailRes {

    private String logId;

    private String schoolId;

    private String schoolName;

    private String studentId;

    private String employeeId;

    private String studentName;

    private String employeeName;

    private StudentPayType payType;

    private Integer money;

    private String remark;

}
