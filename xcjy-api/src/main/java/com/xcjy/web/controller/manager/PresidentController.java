package com.xcjy.web.controller.manager;

import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.ChangeSchoolReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.controller.res.PresidentStatRes;
import com.xcjy.web.controller.res.PresidentTeacherStatRes;
import com.xcjy.web.service.ApplicationService;
import com.xcjy.web.service.PresidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by tupeng on 2017/8/12.
 */
@Api(value = "/president", description = "分校长, 总校长相关接口")
@RestController
@RequestMapping("/president")
public class PresidentController {

    @Autowired
    private PresidentService presidentService;

    @Autowired
    private ApplicationService applicationService;

    @RequiresRoles({CommonUtil.SCHOOLMASTER, CommonUtil.SCHOOLMASTER_BOSS})
    @ApiOperation("查看咨询师签约金额统计")
    @GetMapping("/stat/pay")
    public PresidentStatRes payStat(Date startTime, Date endTime) {
        return presidentService.payStat(startTime, endTime);
    }

    @RequiresRoles({CommonUtil.SCHOOLMASTER, CommonUtil.SCHOOLMASTER_BOSS})
    @ApiOperation("查看续费金额统计")
    @GetMapping("/stat/renew")
    public PresidentStatRes renewStat(Date startTime, Date endTime) {
        return presidentService.renewStat(startTime, endTime);
    }

    @RequiresRoles({CommonUtil.SCHOOLMASTER, CommonUtil.SCHOOLMASTER_BOSS})
    @ApiOperation("查看教师课时量统计")
    @GetMapping("/stat/teacher/hour")
    public PresidentTeacherStatRes teacherHourStat(Date startTime, Date endTime) {
        return presidentService.teacherHourStat(startTime, endTime);
    }

    @RequiresRoles({CommonUtil.SCHOOLMASTER})
    @ApiOperation("创建转校申请")
    @PostMapping("/school")
    public CreateIdRes changeSchool(@RequestBody @Valid ChangeSchoolReq req) {
        return applicationService.changeSchool(req);
    }

    @RequiresRoles({CommonUtil.SCHOOLMASTER})
    @ApiOperation("转校申请审核")
    @PutMapping("/school/{handlerStatus}/{processId}")
    public void auditChangeSchool(@PathVariable String processId, @PathVariable HandlerStatusType handlerStatus, String remark) {
        applicationService.auditChangeSchool(processId, handlerStatus, remark);
    }

}
