package com.xcjy.web.controller.manager;

import com.xcjy.web.controller.res.PresidentStatRes;
import com.xcjy.web.controller.res.PresidentTeacherStatRes;
import com.xcjy.web.service.PresidentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/12.
 */
@RestController
@RequestMapping("/president")
public class PresidentController {

    @Autowired
    private PresidentService presidentService;

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

}
