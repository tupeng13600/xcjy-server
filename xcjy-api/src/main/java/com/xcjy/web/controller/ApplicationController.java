package com.xcjy.web.controller;

import com.xcjy.web.controller.req.BackMoneyCreateReq;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("创建退费申请")
    @PostMapping
    public void backMoney(@RequestBody @Valid BackMoneyCreateReq req){

    }

}
