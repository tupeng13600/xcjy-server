package com.xcjy.web.controller.manager;

import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.req.StudentCourseScheduleReq;
import com.xcjy.web.controller.req.TeacherScheduleStatReq;
import com.xcjy.web.controller.res.StmanagerStatRes;
import com.xcjy.web.controller.res.StudentAssetsRes;
import com.xcjy.web.controller.res.TeacherScheduleRes;
import com.xcjy.web.controller.res.TeacherScheduleStatRes;
import com.xcjy.web.service.CourseScheduleService;
import com.xcjy.web.service.CourseScheduleStudentService;
import com.xcjy.web.service.StmanagerStudentService;
import com.xcjy.web.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/11.
 */
@RestController
@RequestMapping("/stmanager")
public class StmanagerController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseScheduleStudentService courseScheduleStudentService;

    @Autowired
    private CourseScheduleService courseScheduleService;

    @Autowired
    private StmanagerStudentService stmanagerStudentService;

    @ApiOperation("获取学生资产信息[学管师,咨询师]")
    @GetMapping("/assets")
    public List<StudentAssetsRes> getAssets(PageReq page) {
        return studentService.getAssets(page);
    }

    @ApiOperation("为学生安排课表")
    @PostMapping("/course/schedule")
    public void courseSchedule(@RequestBody @Valid StudentCourseScheduleReq req) {
        courseScheduleStudentService.createSchedule(req);
    }

    @ApiOperation("获取教师课表信息")
    @GetMapping("/teacher/schedule")
    public List<TeacherScheduleRes> getTeacherSchedule(@RequestParam Boolean finish, PageReq page){
        return courseScheduleService.getTeacherSchedule(finish, page);
    }

    @ApiOperation("获取教师课时统计信息")
    @GetMapping("/teacher/schedule/stat")
    public List<TeacherScheduleStatRes> getTeacherScheduleStat(TeacherScheduleStatReq req, PageReq page){
        return courseScheduleService.getTSStat(req, page);
    }

    @ApiOperation("获取学管师续费|退费统计列表")
    @GetMapping("/stmanager/back")
    public List<StmanagerStatRes> getStmanagerBack(TeacherScheduleStatReq req, PageReq page){
        return stmanagerStudentService.getStmanagerBack(req, page);
    }

}
