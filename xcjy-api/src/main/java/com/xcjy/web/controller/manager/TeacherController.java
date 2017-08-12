package com.xcjy.web.controller.manager;

import com.xcjy.web.controller.res.TeacherHourStatRes;
import com.xcjy.web.controller.res.TeacherScheduleRes;
import com.xcjy.web.service.CourseScheduleService;
import com.xcjy.web.service.CourseTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private CourseScheduleService courseScheduleService;


    @ApiOperation("查看自己的课表")
    @GetMapping("/schedule")
    public List<TeacherScheduleRes> getSchedule(){
        return courseScheduleService.getTeacherSchedule();
    }

    @ApiOperation("查看自己的课表")
    @GetMapping("/course/stat")
    public TeacherHourStatRes getHourStat(Date startTime, Date endTime){
        return courseScheduleService.statHour4Teacher(startTime, endTime);
    }

}
