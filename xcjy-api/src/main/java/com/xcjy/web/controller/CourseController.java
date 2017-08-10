package com.xcjy.web.controller;

import com.xcjy.web.controller.req.CourseTeacherCreateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.CourseService;
import com.xcjy.web.bean.Course;
import com.xcjy.web.controller.req.CourseCreateReq;
import com.xcjy.web.controller.req.CourseUpdateReq;
import com.xcjy.web.service.CourseTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseTeacherService courseTeacherService;

    @ApiOperation("创建课程")
    @PostMapping
    public CreateIdRes create(@RequestBody @Valid CourseCreateReq req) {
        return courseService.create(req);
    }

    @ApiOperation("修改课程")
    @PutMapping
    public void update(@RequestBody @Valid CourseUpdateReq req) {
        courseService.update(req);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public void deleteLogic(@PathVariable String id) {
        courseService.deleteLogic(id);
    }

    @ApiOperation("获取课程列表")
    @GetMapping
    public List<Course> list(PageReq page) {
        return courseService.list(page);
    }

    @ApiOperation("保存课程教师分配信息")
    @PostMapping("/teacher")
    public void teacher(@RequestBody @Valid CourseTeacherCreateReq req){
        courseTeacherService.save(req);
    }

}
