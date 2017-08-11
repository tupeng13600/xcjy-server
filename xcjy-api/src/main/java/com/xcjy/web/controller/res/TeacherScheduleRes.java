package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/11.
 */
@Data
public class TeacherScheduleRes {

    private String courseId;

    private String teacherId;

    private String teacherName;

    private String courseName;

    private Date startTime;

    private Date endTime;
}
