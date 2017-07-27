package com.xcjy.web.controller;

import com.xcjy.web.service.StudentService;
import com.xcjy.web.bean.Student;
import com.xcjy.web.controller.req.StudentCreateReq;
import com.xcjy.web.controller.req.StudentUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public void create(StudentCreateReq req) {
        studentService.create(req);
    }

    @PutMapping
    public void update(StudentUpdateReq req) {
        studentService.update(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        studentService.deleteLogic(id);
    }

    @GetMapping
    public List<Student> list() {
        return studentService.list();
    }

}
