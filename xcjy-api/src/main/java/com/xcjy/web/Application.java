package com.xcjy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by tupeng on 2017/7/27.
 * <p>
 * 项目访问入口
 */
@SpringBootApplication
@MapperScan("com.xcjy.web.mapper")
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
