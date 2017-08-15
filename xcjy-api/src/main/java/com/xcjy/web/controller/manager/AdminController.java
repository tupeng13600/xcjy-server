package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.User;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.SchoolCreateReq;
import com.xcjy.web.controller.req.SchoolUpdateReq;
import com.xcjy.web.controller.req.UserBaseUpdateReq;
import com.xcjy.web.controller.req.UserRoleUpdateReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.SchoolService;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/8/13.
 * 管理员专用接口
 */
@Api(value = "/admin", description = "管理员专用接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @RequiresRoles(CommonUtil.SUPER_ADMIN)
    @ApiOperation("获取用户列表")
    @GetMapping("/user")
    public List<User> listAll() {
        return userService.getAll();
    }

    @RequiresRoles({CommonUtil.SUPER_ADMIN})
    @ApiOperation("修改用户密码")
    @PutMapping("/user/pwd")
    public void updatePassword(@RequestBody @Valid UserBaseUpdateReq req) {
        userService.updatePassword(req);
    }

    @RequiresRoles({CommonUtil.SUPER_ADMIN})
    @ApiOperation("修改用户角色")
    @PutMapping("/user/role")
    public void updateRole(@RequestBody @Valid UserRoleUpdateReq req) {
        userService.updateRole(req);
    }

    @RequiresRoles({CommonUtil.SUPER_ADMIN})
    @ApiOperation("创建校区")
    @PostMapping("/school")
    public CreateIdRes create(@RequestBody @Valid SchoolCreateReq req) {
        return schoolService.create(req);
    }

    @RequiresRoles({CommonUtil.SUPER_ADMIN})
    @ApiOperation("修改校区信息")
    @PutMapping("/school")
    public void update(@RequestBody @Valid SchoolUpdateReq req) {
        schoolService.update(req);
    }

}
