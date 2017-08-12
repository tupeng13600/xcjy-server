package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CourseStudent {
    private String id;

    private String schoolId;

    private String studentId;

    private String courseId;

    private Integer buyHour;

    private Integer usedHour;

    private Integer score;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}