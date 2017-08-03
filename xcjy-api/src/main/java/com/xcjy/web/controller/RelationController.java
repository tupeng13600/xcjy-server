package com.xcjy.web.controller;

import com.xcjy.web.controller.req.CounselorStudentCreateReq;
import com.xcjy.web.controller.req.StmanagerStudentCreateReq;
import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.service.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by tupeng on 2017/7/25.
 */
@RestController
@RequestMapping("/relation")
public class RelationController {

    @Autowired
    private RelationService relationService;

    /**
     * 分配学生给咨询师
     * @param req
     */
    @ApiOperation("分配学生给咨询师")
    @PostMapping("/counselor/student")
    public void createCounselorStudent(@RequestBody @Valid CounselorStudentCreateReq req){
        relationService.counselorStudent(req);
    }

    /**
     * 分配学生给学管师
     * @param req
     */
    @ApiOperation("分配学生给学管师")
    @PostMapping("/stmanager/student")
    public void createStmanagerStudent(@RequestBody @Valid StmanagerStudentCreateReq req) {
        relationService.stmanagerStudent(req);
    }

    /**
     * 学生缴费
     * @param req
     */
    @ApiOperation("学生缴费")
    @PutMapping("/student/pay")
    public void studentPay(@RequestBody @Valid StudentPayReq req) {
        relationService.studentPay(req);
    }

}
