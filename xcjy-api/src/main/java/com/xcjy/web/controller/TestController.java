package com.xcjy.web.controller;

import com.xcjy.web.common.SchoolThreadLocal;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tupeng on 2017/7/19.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    @RequiresRoles({"teacher"})
    public Map<String, Object> get(){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("schoolId", SchoolThreadLocal.getSchoolId());
        return jsonObject;
    }

    @GetMapping("/get1")
    @RequiresRoles({"student"})
    public Map<String, Object> get1(){
        return new HashMap<>();
    }

    @GetMapping("/get2")
    public Map<String, Object> get2(){
        return new HashMap<>();
    }

}
