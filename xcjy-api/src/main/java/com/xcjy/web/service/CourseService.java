package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseCreateReq;
import com.xcjy.web.controller.req.CourseUpdateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.mapper.CourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public void create(CourseCreateReq req){
        Course course = new Course();
        BeanUtils.copyProperties(req, course);
        course.setSelectedNum(0);
        course.setBackNum(0);
        courseMapper.insert(course);
    }

    public void update(CourseUpdateReq req) {
        Course course = courseMapper.getById(req.getId());
        if(null == course) {
            throw new EducationException("课程不存在");
        }
        BeanUtils.copyProperties(req, course);
        courseMapper.update(course);
    }

    public void deleteLogic(String id) {
        courseMapper.deleteLogic(id, new Date());
    }

    public List<Course> list(PageReq page){
        CurrentThreadLocal.setPageReq(page);
        return courseMapper.getAll();
    }

}
