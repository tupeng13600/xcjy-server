package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class StudentScheduleRes {

    private String courseScheduleStudentId;

    private String courseScheduleId;

    private String studentId;

    private String studentName;

    private String gradeName;

    private String courseName;

    private Date startTime;

    private Date endTime;

    private Boolean finish;

}
