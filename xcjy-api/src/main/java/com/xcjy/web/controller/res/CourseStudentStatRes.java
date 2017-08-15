package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/15.
 */
@Data
public class CourseStudentStatRes {

    private String studentId;

    private String courseId;

    private String studentName;

    private String courseName;

    private Integer buyHour;

    private Integer usedHour;

    private Date buyTime;

}
