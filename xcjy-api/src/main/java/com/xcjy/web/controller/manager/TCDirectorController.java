package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.Grade;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.CourseService;
import com.xcjy.web.service.CourseTeacherService;
import com.xcjy.web.service.GradeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/13.\
 * 教研主任
 */
@RestController
@RequestMapping("/director")
public class TCDirectorController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @ApiOperation("创建班组")
    @PostMapping("/grade")
    public CreateIdRes create(@RequestBody @Valid GradeCreateReq req) {
        return gradeService.create(req);
    }

    @ApiOperation("编辑班组")
    @PutMapping("/grade")
    public void create(@RequestBody @Valid GradeUpdateReq req) {
        gradeService.update(req);
    }

    @ApiOperation("获取班组列表")
    @GetMapping("/grade")
    public List<Grade> list() {
        return gradeService.getAll();
    }

    @ApiOperation("删除班组")
    @PostMapping("/grade/{id}")
    public void delete(@PathVariable String id) {
        gradeService.delete(id);
    }

    @ApiOperation("创建课程")
    @PostMapping("/course")
    public CreateIdRes create(@RequestBody @Valid CourseCreateReq req) {
        return courseService.create(req);
    }

    @ApiOperation("修改课程")
    @PutMapping("/course")
    public void update(@RequestBody @Valid CourseUpdateReq req) {
        courseService.update(req);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/course/{id}")
    public void deleteLogic(@PathVariable String id) {
        courseService.deleteLogic(id);
    }

    @ApiOperation("获取课程列表")
    @GetMapping("/course")
    public List<Course> list(PageReq page) {
        return courseService.list(page);
    }

    @ApiOperation("保存课程教师分配信息")
    @PostMapping("/teacher")
    public void teacher(@RequestBody @Valid CourseTeacherCreateReq req){
        courseTeacherService.save(req);
    }


}
