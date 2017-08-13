package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.User;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.req.UserBaseUpdateReq;
import com.xcjy.web.controller.req.UserRoleUpdateReq;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private UserService userService;

    @ApiOperation("获取用户列表")
    @GetMapping
    public List<User> listAll(PageReq pageReq) {
        return userService.getAll(pageReq);
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/pwd")
    public void updatePassword(@RequestBody @Valid UserBaseUpdateReq req) {
        userService.updatePassword(req);
    }

    @ApiOperation("修改用户角色")
    @PutMapping("/role")
    public void updateRole(@RequestBody @Valid UserRoleUpdateReq req) {
        userService.updateRole(req);
    }

}
