package com.xcjy.web.bean;

import com.xcjy.web.common.enums.StudentPayType;
import lombok.Data;

import java.util.Date;

@Data
public class StudentPayLog {
    private String id;

    private String schoolId;

    private String studentId;

    private String employeeId;

    private StudentPayType opPayType;

    private Integer money;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}