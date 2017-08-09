package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CourseSchedule {
    private String id;

    private String schoolId;

    private String courseId;

    private String employeeId;

    private Date startTime;

    private Date endTime;

    private Integer studyTime;

    private Boolean finish;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}