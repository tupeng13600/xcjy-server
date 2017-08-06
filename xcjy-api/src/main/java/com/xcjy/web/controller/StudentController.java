package com.xcjy.web.controller;

import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.service.StudentService;
import com.xcjy.web.bean.Student;
import com.xcjy.web.controller.req.StudentCreateReq;
import com.xcjy.web.controller.req.StudentUpdateReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation("创建学生")
    @PostMapping
    public void create(@RequestBody @Valid StudentCreateReq req) {
        studentService.create(req);
    }

    @ApiOperation("修改学生信息")
    @PutMapping
    public void update(@RequestBody @Valid StudentUpdateReq req) {
        studentService.update(req);
    }

    @ApiOperation("删除学生")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        studentService.deleteLogic(id);
    }

    @ApiOperation("获取学生列表")
    @GetMapping
    public List<Student> list(PageReq pageReq) {
        return studentService.list(pageReq);
    }

}
