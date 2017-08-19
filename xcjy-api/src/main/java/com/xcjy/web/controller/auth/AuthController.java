package com.xcjy.web.controller.auth;

import com.xcjy.auth.cache.TokenThreadLocal;
import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tupeng on 2017/7/19.
 */
@Api(value = "/auth", description = "权限相关接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @ApiOperation("登陆接口")
    @PutMapping("/login")
    public Map<String, String> valid(@RequestParam String username, @RequestParam String password) {
        Map<String, String> result = new HashMap<>();
        result.put("accessToken", TokenThreadLocal.get());
        return result;
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/user/info")
    public User getUserInfo() {
        String username = UserUtil.getCurrentUserName();
        return userService.getByUsernameOrPhone(username, username);
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
        TokenThreadLocal.remove(); //清除token
    }

}
