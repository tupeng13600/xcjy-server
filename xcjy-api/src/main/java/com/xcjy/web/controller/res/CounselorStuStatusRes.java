package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.CounselorStudentStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class CounselorStuStatusRes {

    private String studentId;

    private String employeeId;

    private String studentName;

    private String studentPhone;

    private String employeeName;

    private String employeePhone;

    private Date updateTime;

    private CounselorStudentStatusType status;

}
