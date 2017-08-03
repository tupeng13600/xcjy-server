package com.xcjy.web.controller.auth;

import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tupeng on 2017/7/19.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @ApiOperation("登陆接口")
    @PutMapping("/login")
    public void valid(@RequestParam String username, @RequestParam String password){
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/user/info")
    public User getUserInfo(){
        String username = UserUtil.getCurrentUserName();
        return userService.getByUsernameOrPhone(username, username);
    }

}
