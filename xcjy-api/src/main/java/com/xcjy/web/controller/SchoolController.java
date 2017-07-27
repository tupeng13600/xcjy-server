package com.xcjy.web.controller;

import com.xcjy.web.service.SchoolService;
import com.xcjy.web.bean.School;
import com.xcjy.web.controller.req.SchoolCreateReq;
import com.xcjy.web.controller.req.SchoolUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public void create(SchoolCreateReq req) {
        schoolService.create(req);
    }

    /**
     * 更新校区
     * @param req
     */
    @PutMapping
    public void update(SchoolUpdateReq req) {
        schoolService.update(req);
    }

    /**
     * 获取校区列表
     * @return
     */
    @GetMapping
    public List<School> list() {
        return schoolService.list();
    }

}
