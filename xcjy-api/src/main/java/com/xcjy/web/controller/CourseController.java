package com.xcjy.web.controller;

import com.xcjy.web.service.CourseService;
import com.xcjy.web.bean.Course;
import com.xcjy.web.controller.req.CourseCreateReq;
import com.xcjy.web.controller.req.CourseUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public void create(CourseCreateReq req) {
        courseService.create(req);
    }

    @PutMapping
    public void update(CourseUpdateReq req) {
        courseService.update(req);
    }

    @DeleteMapping("/{id}")
    public void deleteLogic(@PathVariable String id) {
        courseService.deleteLogic(id);
    }

    @GetMapping
    public List<Course> list() {
        return courseService.list();
    }

}
