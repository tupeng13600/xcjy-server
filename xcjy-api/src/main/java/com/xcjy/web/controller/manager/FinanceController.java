package com.xcjy.web.controller.manager;

import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.controller.res.StudentPayLogDetailRes;
import com.xcjy.web.service.FinanceService;
import com.xcjy.web.service.RelationService;
import com.xcjy.web.service.StudentPayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 * 人事专员出纳, 财务
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private StudentPayLogService studentPayLogService;

    /**
     * 学生缴费
     * @param req
     */
    @ApiOperation("学生缴费")
    @PutMapping("/student/pay")
    public void studentPay(@RequestBody @Valid StudentPayReq req) {
        financeService.studentPay(req);
    }

    /**
     * 查看各校区缴费，退费详细日志
     * @param schoolId
     */
    @ApiOperation("查看各校区学生缴费日志")
    @PutMapping("/student/pay/log/{schoolId}")
    public List<StudentPayLogDetailRes> getPayLogDetailRes(@PathVariable String schoolId){
        return studentPayLogService.getPayLogDetailRes(schoolId);
    }

}
