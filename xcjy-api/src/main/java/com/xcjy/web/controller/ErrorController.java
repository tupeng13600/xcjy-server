package com.xcjy.web.controller;

import com.xcjy.web.common.exception.EducationException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tupeng on 2017/8/3.
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @ApiOperation("springboot同意错误入口，无需使用")
    @GetMapping("/404")
    public void error404(){
        throw new EducationException("资源不存在");
    }

}
