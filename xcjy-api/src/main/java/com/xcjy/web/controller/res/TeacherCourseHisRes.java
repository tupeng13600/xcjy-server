package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherCourseHisRes {

    private String courseId;

    private String gradeId;

    private String gradeName;

    private String courseName;

    private Date startTime;

    private Date endTime;

    private Integer studyTime;

    private Boolean finish;

}
