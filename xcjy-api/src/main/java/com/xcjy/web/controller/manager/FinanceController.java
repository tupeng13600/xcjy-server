package com.xcjy.web.controller.manager;

import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.service.FinanceService;
import com.xcjy.web.service.RelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by tupeng on 2017/8/12.
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    /**
     * 学生缴费
     * @param req
     */
    @ApiOperation("学生缴费")
    @PutMapping("/student/pay")
    public void studentPay(@RequestBody @Valid StudentPayReq req) {
        financeService.studentPay(req);
    }

}
