package com.xcjy.web.controller;

import com.xcjy.web.controller.req.RoleCreateReq;
import com.xcjy.web.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("创建角色")
    @PostMapping
    public void create(@RequestBody @Valid RoleCreateReq req) {
        roleService.create(req);
    }

}
