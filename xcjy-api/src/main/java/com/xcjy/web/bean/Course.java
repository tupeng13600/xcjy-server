package com.xcjy.web.bean;

import com.xcjy.web.common.enums.CourseType;
import lombok.Data;

import java.util.Date;

@Data
public class Course {

    private String id;

    private String schoolId;

    private String gradeId;

    private String name;

    private CourseType type;

    private Integer studentNum;

    private Integer price;

    private Integer studyHour;

    private Integer selectedNum;

    private Integer backNum;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}