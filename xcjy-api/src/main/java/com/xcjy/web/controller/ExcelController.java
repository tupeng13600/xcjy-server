package com.xcjy.web.controller;

import com.xcjy.web.service.ExcelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by tupeng on 2017/7/24.
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @ApiOperation("excel导入学生信息")
    @PostMapping("/student")
    public void student(@RequestParam MultipartFile file) throws IOException {
        excelService.importStudent(file);
    }

}
