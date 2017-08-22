package com.xcjy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by tupeng on 2017/8/22.
 */
@Controller
public class IndexController {

    @GetMapping("/xcjy/")
    public String index(){
        return "forward:/xcjy/index.html";
    }

}
