package com.xcjy.web.bean;

import com.xcjy.web.common.enums.CounselorStudentStatusType;
import lombok.Data;

import java.util.Date;

@Data
public class CounselorStudent {
    private String id;

    private String schoolId;

    private String employeeId;

    private String studentId;

    private Integer money;

    private Integer hasBack;

    private CounselorStudentStatusType status;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}