package com.xcjy.web.controller;

import com.xcjy.web.controller.req.CourseScheduleCreateReq;
import com.xcjy.web.controller.req.CourseScheduleUpdateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.ScheduleRes;
import com.xcjy.web.service.CourseScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/9.
 */
@RestController
@RequestMapping("/course/schedule")
public class CourseScheduleController {

    @Autowired
    private CourseScheduleService courseScheduleService;

    @ApiOperation("创建课表")
    @PostMapping
    public void create(@RequestBody @Valid CourseScheduleCreateReq req) {
        courseScheduleService.create(req);
    }

    @ApiOperation("更新课表")
    @PutMapping
    public void update(@RequestBody @Valid CourseScheduleUpdateReq req) {
        courseScheduleService.update(req);
    }

    @ApiOperation("获取课表列表")
    @GetMapping
    public List<ScheduleRes> listSchedule(PageReq req) {
        return courseScheduleService.list(req);
    }

}
