package com.xcjy.web.controller;

import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.SchoolService;
import com.xcjy.web.bean.School;
import com.xcjy.web.controller.req.SchoolCreateReq;
import com.xcjy.web.controller.req.SchoolUpdateReq;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * 创建校区
     * @param req
     */
    @ApiOperation("创建校区")
    @PostMapping
    public CreateIdRes create(@RequestBody @Valid SchoolCreateReq req) {
        return schoolService.create(req);
    }

    /**
     * 更新校区
     * @param req
     */
    @ApiOperation("修改校区信息")
    @PutMapping
    public void update(@RequestBody @Valid SchoolUpdateReq req) {
        schoolService.update(req);
    }

    /**
     * 获取校区列表
     * @return
     */
    @ApiOperation("获取校区列表")
    @GetMapping
    public List<School> list() {
        return schoolService.list();
    }

}
