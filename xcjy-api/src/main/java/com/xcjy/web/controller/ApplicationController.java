package com.xcjy.web.controller;

import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/money")
    public void backMoney(@RequestBody @Valid BackMoneyCreateReq req) {
        applicationService.backMoney(req);
    }

    @ApiOperation("退费申请审核")
    @PutMapping("/money/{handlerStatus}/{processId}")
    public void audit(@PathVariable String processId, @PathVariable HandlerStatusType handlerStatus) {
        applicationService.auditBackMoney(processId, handlerStatus);
    }

}
