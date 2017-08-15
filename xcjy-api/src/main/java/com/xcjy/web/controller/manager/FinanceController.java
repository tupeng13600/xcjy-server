package com.xcjy.web.controller.manager;

import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.controller.res.FinanceStudentStatRes;
import com.xcjy.web.controller.res.StudentPayLogDetailRes;
import com.xcjy.web.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 * 人事专员出纳, 财务
 */
@Api(value = "/finance", description = "人事专员出纳, 财务相关接口")
@RestController
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private StudentPayLogService studentPayLogService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private StudentService studentService;

    @RequiresRoles({CommonUtil.FINANCE})
    @ApiOperation("学生缴费")
    @PutMapping("/student/pay")
    public void studentPay(@RequestBody @Valid StudentPayReq req) {
        financeService.studentPay(req);
    }

    @RequiresRoles({CommonUtil.FINANCE})
    @ApiOperation("查看各校区学生缴费日志")
    @PutMapping("/student/pay/log/{schoolId}")
    public List<StudentPayLogDetailRes> getPayLogDetailRes(@PathVariable String schoolId){
        return studentPayLogService.getPayLogDetailRes(schoolId);
    }

    @RequiresRoles({CommonUtil.FINANCE})
    @ApiOperation("导入已缴费学生信息")
    @PostMapping("/student/paid")
    public void studentPaid(@RequestParam MultipartFile file) throws IOException {
        excelService.importStudentPaid(file);
    }

    @RequiresRoles({CommonUtil.FINANCE})
    @ApiOperation("获取学生缴费统计")
    @PostMapping("/student/stat/{schoolId}")
    public List<FinanceStudentStatRes> studentPaid(@PathVariable String schoolId) throws IOException {
        return studentService.getBySchoolId(schoolId);
    }


}
