package com.xcjy.web.controller;

import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by tupeng on 2017/8/5.
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation("创建退费申请")
    @PostMapping
    public void backMoney(@RequestBody @Valid BackMoneyCreateReq req){
        applicationService.backMoney(req);
    }

}
