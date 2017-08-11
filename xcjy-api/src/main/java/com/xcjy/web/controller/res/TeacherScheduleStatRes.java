package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/11.
 */
@Data
public class TeacherScheduleStatRes {

    private String teacherId;

    private String teacherName;

    private String teacherPhone;

    private Integer finishHour = 0;

    private Integer totalHour = 0;

}
