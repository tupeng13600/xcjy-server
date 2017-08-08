package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CourseTeacher {

    private String id;

    private String schoolId;

    private String courseId;

    private String teacherId;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}