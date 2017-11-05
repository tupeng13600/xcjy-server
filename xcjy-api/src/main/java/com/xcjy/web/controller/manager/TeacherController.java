package com.xcjy.web.controller.manager;

import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.res.StudentScoreRes;
import com.xcjy.web.controller.res.TeacherCourseHisRes;
import com.xcjy.web.controller.res.TeacherHourStatRes;
import com.xcjy.web.controller.res.TeacherScheduleRes;
import com.xcjy.web.service.CourseScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@Api(value = "/teacher", description = "教师相关接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private CourseScheduleService courseScheduleService;


    @RequiresRoles({CommonUtil.TEACHER})
    @ApiOperation("查看自己的课表")
    @GetMapping("/schedule")
    public List<TeacherScheduleRes> getSchedule(){
        return courseScheduleService.getTeacherSchedule();
    }

    @RequiresRoles({CommonUtil.TEACHER})
    @ApiOperation("查看自己的课时统计信息")
    @GetMapping("/course/stat")
    public TeacherHourStatRes getHourStat(Date startTime, Date endTime){
        return courseScheduleService.statHour4Teacher(startTime, endTime);
    }

    @RequiresRoles({CommonUtil.TEACHER})
    @ApiOperation("查看上课历史")
    @GetMapping("/course/his")
    public List<TeacherCourseHisRes> getCourseHis(){
        return courseScheduleService.getHis4Teacher();
    }

    @RequiresRoles({CommonUtil.TEACHER})
    @ApiOperation("查看学生成绩")
    @GetMapping("/course/score")
    public List<StudentScoreRes> getScore(){
        return courseScheduleService.getScore4Teacher();
    }

}
