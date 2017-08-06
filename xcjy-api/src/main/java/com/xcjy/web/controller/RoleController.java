package com.xcjy.web.controller;

import com.xcjy.web.controller.res.RoleRes;
import com.xcjy.web.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取全部角色列表")
    @GetMapping
    public List<RoleRes> list() {
        return roleService.listAll();
    }

}
