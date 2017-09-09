package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.CourseScheduleService;
import com.xcjy.web.service.CourseService;
import com.xcjy.web.service.CourseTeacherService;
import com.xcjy.web.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/13.\
 * 教研主任
 */
@Api(value = "/director", description = "教研主任相关接口")
@RestController
@RequestMapping("/director")
public class TCDirectorController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @Autowired
    private CourseScheduleService courseScheduleService;

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("创建班组")
    @PostMapping("/grade")
    public CreateIdRes create(@RequestBody @Valid GradeCreateReq req) {
        return gradeService.create(req);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("编辑班组")
    @PutMapping("/grade")
    public void create(@RequestBody @Valid GradeUpdateReq req) {
        gradeService.update(req);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("删除班组")
    @PostMapping("/grade/{id}")
    public void delete(@PathVariable String id) {
        gradeService.delete(id);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("创建课程")
    @PostMapping("/course")
    public CreateIdRes create(@RequestBody @Valid CourseCreateReq req) {
        return courseService.create(req);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("修改课程")
    @PutMapping("/course")
    public void update(@RequestBody @Valid CourseUpdateReq req) {
        courseService.update(req);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("删除课程")
    @DeleteMapping("/course/{id}")
    public void deleteLogic(@PathVariable String id) {
        courseService.deleteLogic(id);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("保存课程教师分配信息")
    @PostMapping("/teacher")
    public void teacher(@RequestBody @Valid CourseTeacherCreateReq req) {
        courseTeacherService.save(req);
    }

    @RequiresRoles({CommonUtil.TEACHER_DIRECTOR})
    @ApiOperation("根据课程获取教师列表")
    @PostMapping("/course/teacher/{courseId}")
    public List<Employee> getTeacherByCourseId(@PathVariable String courseId) {
        return courseScheduleService.getByCourseId(courseId);
    }

}
