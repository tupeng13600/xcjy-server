package com.xcjy.web.controller.manager;

import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import com.xcjy.web.controller.req.ChangeSchoolReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.controller.res.PresidentStatRes;
import com.xcjy.web.controller.res.PresidentTeacherStatRes;
import com.xcjy.web.controller.res.ProcessRes;
import com.xcjy.web.service.ApplicationService;
import com.xcjy.web.service.PresidentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@RestController
@RequestMapping("/president")
public class PresidentController {

    @Autowired
    private PresidentService presidentService;

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation("查看咨询师签约金额统计")
    @GetMapping("/stat/pay")
    public PresidentStatRes payStat(Date startTime, Date endTime) {
        return presidentService.payStat(startTime, endTime);
    }

    @ApiOperation("查看续费金额统计")
    @GetMapping("/stat/renew")
    public PresidentStatRes renewStat(Date startTime, Date endTime) {
        return presidentService.renewStat(startTime, endTime);
    }

    @ApiOperation("查看教师课时量统计")
    @GetMapping("/stat/teacher/hour")
    public PresidentTeacherStatRes teacherHourStat(Date startTime, Date endTime) {
        return presidentService.teacherHourStat(startTime, endTime);
    }

    @ApiOperation("退费申请审核")
    @PutMapping("/money/{handlerStatus}/{processId}")
    public void auditMoney(@PathVariable String processId, @PathVariable HandlerStatusType handlerStatus, String remark) {
        applicationService.auditBackMoney(processId, handlerStatus, remark);
    }

    @ApiOperation("创建转校申请")
    @PostMapping("/school")
    public CreateIdRes changeSchool(@RequestBody @Valid ChangeSchoolReq req) {
        return applicationService.changeSchool(req);
    }

    @ApiOperation("转校申请申请审核")
    @PutMapping("/school/{handlerStatus}/{processId}")
    public void auditChangeSchool(@PathVariable String processId, @PathVariable HandlerStatusType handlerStatus, String remark) {
        applicationService.auditChangeSchool(processId, handlerStatus, remark);
    }
    @ApiOperation("获取审核列表")
    @GetMapping("/money/{processLog}/{handlerStatus}")
    public List<ProcessRes> getBackMoneyProcessList(@PathVariable HandlerStatusType handlerStatus, @PathVariable ProcessLogType processLog) {
        return applicationService.listProcess(handlerStatus, processLog);
    }


}
