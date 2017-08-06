package com.xcjy.web.controller;

import com.xcjy.web.bean.User;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/18.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册新的用户
     *
     * @param req
     */
    @ApiOperation("注册用户，仅供测试使用")
    @PostMapping
    public void register(@RequestBody @Valid RegisterReq req) {
        User user = userService.getByUsernameOrPhone(req.getUsername(), req.getPhone());
        if (null != user) {
            throw new EducationException("用户名或手机号码已经被占用");
        }
        userService.insert(req);
    }

    @ApiOperation("获取用户列表")
    @GetMapping
    public List<User> listAll(PageReq pageReq) {
        return userService.getAll(pageReq);
    }

    @ApiOperation("修改用户密码")
    @PutMapping("/pwd")
    public void updatePassword(UserBaseUpdateReq req) {
        userService.updatePassword(req);
    }

    @ApiOperation("修改用户角色")
    @PutMapping("/pwd")
    public void updateRole(UserRoleUpdateReq req) {
        userService.updateRole(req);
    }

    @ApiOperation("修改自身用户密码")
    @PutMapping("/pwd")
    public void updateSelfPassword(UserPwdSelfUpdateReq req) {
        userService.updateSelfPassword(req);
    }

}
