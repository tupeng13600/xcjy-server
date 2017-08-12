package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class TeacherHourStatRes {

    private Integer totalHours = 0;

    private List<TeacherCourseHourStat> detail;

}
