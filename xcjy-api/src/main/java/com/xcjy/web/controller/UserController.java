package com.xcjy.web.controller;

import com.xcjy.web.service.UserService;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.RegisterReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void register(@RequestBody RegisterReq req) {
        User user = userService.getByUsernameOrPhone(req.getUsername(), req.getPhone());
        if (null != user) {
            throw new EducationException("用户名或手机号码已经被占用");
        }
        userService.insert(req);
    }

}
