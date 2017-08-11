package com.xcjy.web.controller;

import com.xcjy.web.controller.req.*;
import com.xcjy.web.controller.res.CounselorAssesSignRes;
import com.xcjy.web.controller.res.CounselorStatRes;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.EmployeeService;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.service.StudentAssetService;
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

    @Autowired
    private StudentAssetService studentAssetService;

    @ApiOperation("创建员工")
    @PostMapping
    public CreateIdRes create(@RequestBody @Valid EmployeeCreateReq req){
        return employeeService.create(req);
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
    public List<Employee> list(PageReq pageReq){
        return employeeService.list(pageReq);
    }

    @ApiOperation("咨询师查看金额和总和")
    @GetMapping("/student/stat")
    public CounselorStatRes getCounselorStat(@RequestBody @Valid CounselorStatReq req, PageReq page){
        return studentAssetService.getCounselorStat(req, page);
    }


    @ApiOperation("咨询主任获取咨询师签约数据")
    @GetMapping("/counselor/stat")
    public List<CounselorAssesSignRes> getAssetsSign(@RequestBody @Valid AssetsSignReq req, PageReq page){
        return studentAssetService.getAssetsSign(req, page);
    }

}
