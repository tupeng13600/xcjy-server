package com.xcjy.web.controller;

import com.xcjy.web.service.EmployeeService;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.controller.req.EmployeeCreateReq;
import com.xcjy.web.controller.req.EmployeeUpdateReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("创建员工")
    @PostMapping
    public void create(@RequestBody @Valid EmployeeCreateReq req){
        employeeService.create(req);
    }

    @ApiOperation("修改员工")
    @PutMapping
    public void update(@RequestBody @Valid EmployeeUpdateReq req) {
        employeeService.update(req);
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        employeeService.deleted(id);
    }

    @ApiOperation("获取员工列表")
    @GetMapping
    public List<Employee> list(){
        return employeeService.list();
    }

}
