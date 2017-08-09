package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CourseScheduleStudent {
    private String id;

    private String courseScheduleId;

    private String studentId;

    private Boolean finish;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}