package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.CourseTeacher;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseTeacherCreateReq;
import com.xcjy.web.mapper.CourseMapper;
import com.xcjy.web.mapper.CourseTeacherMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tupeng on 2017/8/9.
 */
@Service
public class CourseTeacherService {

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Transactional
    public void save(CourseTeacherCreateReq req) {
        Course course = courseMapper.getById(req.getCourseId());
        if (null == course) {
            throw new EducationException("课程信息不存在");
        }
        String schoolId = CurrentThreadLocal.getSchoolId();
        courseTeacherMapper.deleteByCourseId(CurrentThreadLocal.getSchoolId(), req.getCourseId());
        if (CollectionUtils.isNotEmpty(req.getTeacherIds())) {
            for (String id : req.getTeacherIds()) {
                if (!CacheFactory.employeeMap.containsKey(id)) {
                    throw new EducationException("员工：" + id + " 不存在");
                }
            }
            courseTeacherMapper.insertBatch(getCourseTeachers(schoolId, req.getCourseId(), req.getTeacherIds()));
        }
    }


    private List<CourseTeacher> getCourseTeachers(String schoolId, String courseId, Set<String> employeeIds) {
        List<CourseTeacher> courseTeacherList = new ArrayList<>();
        employeeIds.forEach(employeeId -> {
            CourseTeacher courseTeacher = new CourseTeacher();
            courseTeacher.setCourseId(courseId);
            courseTeacher.setSchoolId(schoolId);
            courseTeacher.setTeacherId(employeeId);
            courseTeacherList.add(courseTeacher);
        });
        return courseTeacherList;
    }

}
