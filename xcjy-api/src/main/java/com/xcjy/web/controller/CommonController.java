package com.xcjy.web.controller;

import com.xcjy.web.controller.req.UserPwdSelfUpdateReq;
import com.xcjy.web.controller.res.RoleRes;
import com.xcjy.web.service.RoleService;
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

}
