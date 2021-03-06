package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/9.
 */
@Data
public class ScheduleRes {

    private String courseScheduleId;

    private String schoolId;

    private String courseId;

    private String courseName;

    private String teacherId;

    private String teacherName;

    private Date startTime;

    private Date endTime;

    private Boolean finish;

    private Integer studyTime;

}
