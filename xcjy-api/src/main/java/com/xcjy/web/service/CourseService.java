package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.Grade;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseCreateReq;
import com.xcjy.web.controller.req.CourseUpdateReq;
import com.xcjy.web.controller.res.CourseShowRes;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.mapper.CourseMapper;
import com.xcjy.web.mapper.GradeMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GradeMapper gradeMapper;

    public CreateIdRes create(CourseCreateReq req) {
        Grade grade = gradeMapper.getById(req.getGradeId());
        if (null == grade) {
            throw new EducationException("年级信息不存在");
        }
        Course course = new Course();
        BeanUtils.copyProperties(req, course);
        course.setSchoolId(CurrentThreadLocal.getSchoolId());
        course.setPrice(grade.getPrice());
        course.setSelectedNum(0);
        course.setBackNum(0);
        courseMapper.insert(course);
        return new CreateIdRes(course.getId());
    }

    public void update(CourseUpdateReq req) {
        Course course = courseMapper.getById(req.getId());
        if (null == course) {
            throw new EducationException("课程不存在");
        }
        BeanUtils.copyProperties(req, course);
        courseMapper.update(course);
    }

    public void deleteLogic(String id) {
        courseMapper.deleteLogic(id, new Date());
    }

    public List<CourseShowRes> list() {
        List<Course> courseList = courseMapper.getAll();
        List<CourseShowRes> resList = new ArrayList<>();
        List<Grade> gradeList = gradeMapper.getAll();
        if (CollectionUtils.isNotEmpty(courseList)) {
            CourseShowRes showRes = new CourseShowRes();
            courseList.forEach(course -> {
                BeanUtils.copyProperties(course, showRes);
                for (Grade grade : gradeList) {
                    if (grade.getId().equals(course.getGradeId())) {
                        showRes.setGradeName(grade.getName());
                        break;
                    }
                }
                resList.add(showRes);
            });
        }
        return resList;
    }

}
