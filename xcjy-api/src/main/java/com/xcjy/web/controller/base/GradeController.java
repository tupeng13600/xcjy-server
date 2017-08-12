package com.xcjy.web.controller.base;

import com.xcjy.web.controller.req.GradeCreateReq;
import com.xcjy.web.controller.req.GradeUpdateReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.service.GradeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by tupeng on 2017/8/12.
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("创建班组")
    @PostMapping
    public CreateIdRes create(@RequestBody @Valid GradeCreateReq req) {
        return gradeService.create(req);
    }

    @ApiOperation("编辑班组")
    @PutMapping
    public void create(@RequestBody @Valid GradeUpdateReq req) {
        gradeService.update(req);
    }

    @ApiOperation("删除班组")
    @PostMapping("/{id}")
    public void delete(@PathVariable String id) {
        gradeService.delete(id);
    }

}
