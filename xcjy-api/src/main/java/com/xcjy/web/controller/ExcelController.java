package com.xcjy.web.controller;

import com.xcjy.web.controller.req.CourseStudentReq;
import com.xcjy.web.service.CourseStudentService;
import com.xcjy.web.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by tupeng on 2017/7/24.
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("导入学生信息")
    @PostMapping("/student")
    public void student(@RequestParam MultipartFile file) throws IOException {
        excelService.importStudent(file);
    }


}
