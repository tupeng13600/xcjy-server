package com.xcjy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tupeng on 2017/7/27.
 *
 * 项目访问入口
 *
 */
@SpringBootApplication
@MapperScan("com.xcjy.web.mapper")
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
