package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StudentCourseTeacher {
    private String id;

    private String schoolId;

    private String studentId;

    private String employeeId;

    private String courseId;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}