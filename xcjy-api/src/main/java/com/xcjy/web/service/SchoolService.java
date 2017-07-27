package com.xcjy.web.service;

import com.xcjy.web.bean.School;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.SchoolCreateReq;
import com.xcjy.web.controller.req.SchoolUpdateReq;
import com.xcjy.web.mapper.SchoolMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    /**
     * 创建校区
     * @param req
     */
    public void create(SchoolCreateReq req) {
        School school = new School();
        BeanUtils.copyProperties(req, school);
        schoolMapper.insert(school);
    }

    /**
     * 修改校区
     * @param req
     */
    public void update(SchoolUpdateReq req) {
        School school = schoolMapper.getById(req.getId());
        if(null == school) {
            throw new EducationException("校区不存在");
        }
        BeanUtils.copyProperties(req, school);
        schoolMapper.update(school);
    }

    /**
     * 获取校区列表
     * @return
     */
    public List<School> list() {
        return schoolMapper.getAll();
    }

}
