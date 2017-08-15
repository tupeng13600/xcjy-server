package com.xcjy.web.controller;

import com.xcjy.web.bean.School;
import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import com.xcjy.web.controller.req.UserPwdSelfUpdateReq;
import com.xcjy.web.controller.res.ProcessRes;
import com.xcjy.web.controller.res.RoleRes;
import com.xcjy.web.service.ApplicationService;
import com.xcjy.web.service.RoleService;
import com.xcjy.web.service.SchoolService;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 * 基础公用接口
 */
@Api(value = "/common", description = "公用接口")
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ApplicationService applicationService;

    @ApiOperation("获取全部角色列表")
    @GetMapping("/role")
    public List<RoleRes> list() {
        return roleService.listAll();
    }

    @ApiOperation("修改自身用户密码")
    @PutMapping("/self/pwd")
    public void updateSelfPassword(@RequestBody @Valid UserPwdSelfUpdateReq req) {
        userService.updateSelfPassword(req);
    }

    /**
     * 获取校区列表
     * @return
     */
    @ApiOperation("获取校区列表")
    @GetMapping("/school")
    public List<School> listSchool() {
        return schoolService.list();
    }

    @ApiOperation("获取审核列表")
    @GetMapping("/money/{processLog}/{handlerStatus}")
    public List<ProcessRes> getBackMoneyProcessList(@PathVariable HandlerStatusType handlerStatus, @PathVariable ProcessLogType processLog) {
        return applicationService.listProcess(handlerStatus, processLog);
    }

}
