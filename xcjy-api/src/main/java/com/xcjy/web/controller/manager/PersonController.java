package com.xcjy.web.controller.manager;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.controller.req.EmployeeCreateReq;
import com.xcjy.web.controller.req.EmployeeUpdateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.EmployeeService;
import com.xcjy.web.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by tupeng on 2017/8/13.
 * 人事经理
 */
@Api(value = "/persion", description = "人事经理相关接口")
@RestController
@RequestMapping("/persion")
public class PersonController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExcelService excelService;

    @RequiresRoles({CommonUtil.PERSONNEL_MANAGER})
    @ApiOperation("创建员工")
    @PostMapping("/employee")
    public CreateIdRes create(@RequestBody @Valid EmployeeCreateReq req) {
        return employeeService.create(req);
    }

    @RequiresRoles({CommonUtil.PERSONNEL_MANAGER})
    @ApiOperation("修改员工")
    @PutMapping("/employee")
    public void update(@RequestBody @Valid EmployeeUpdateReq req) {
        employeeService.update(req);
    }

    @RequiresRoles({CommonUtil.PERSONNEL_MANAGER})
    @ApiOperation("删除员工")
    @DeleteMapping("/employee/{id}")
    public void delete(@PathVariable String id) {
        employeeService.deleted(id);
    }

    @RequiresRoles({CommonUtil.PERSONNEL_MANAGER})
    @ApiOperation("获取员工列表")
    @GetMapping("/employee")
    public List<Employee> list() {
        return employeeService.list();
    }

}
