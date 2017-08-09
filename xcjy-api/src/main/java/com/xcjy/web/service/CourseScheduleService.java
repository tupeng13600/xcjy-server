package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.CourseSchedule;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseScheduleCreateReq;
import com.xcjy.web.controller.req.CourseScheduleUpdateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.ScheduleRes;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/9.
 */
@Service
public class CourseScheduleService {

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private CourseScheduleStudentMapper courseScheduleStudentMapper;

    @Transactional
    public void create(CourseScheduleCreateReq req) {

        Course course = courseMapper.getById(req.getCourseId());
        if(null == course) {
            throw new EducationException("课程信息不存在");
        }
        Employee employee = employeeMapper.getById(req.getEmployeeId());
        if(null == employee) {
            throw new EducationException("员工信息不存在");
        }
        CourseSchedule courseSchedule = new CourseSchedule();
        BeanUtils.copyProperties(req, courseSchedule);
        courseSchedule.setSchoolId(CurrentThreadLocal.getSchoolId());
        courseSchedule.setFinish(false);
        courseScheduleMapper.insert(courseSchedule);
    }

    @Transactional
    public void update(CourseScheduleUpdateReq req) {
        CourseSchedule courseSchedule = courseScheduleMapper.getById(req.getId());
        if(null == courseSchedule) {
            throw new EducationException("课表信息不存在");
        }
        if(null == courseMapper.getById(req.getCourseId())) {
            throw new EducationException("课程信息不存在");
        }
        if(null == employeeMapper.getById(req.getEmployeeId())) {
            throw new EducationException("员工信息不存在");
        }
        if(null == courseTeacherMapper.getByCIdAndTId(req.getCourseId(), req.getEmployeeId())) {
            throw new EducationException("该教师无法教授该课程");
        }
        BeanUtils.copyProperties(req, courseSchedule);
        courseScheduleMapper.updateBase(courseSchedule);
    }

    public List<ScheduleRes> list(PageReq req) {
        List<ScheduleRes> resList = new ArrayList<>();
        CurrentThreadLocal.setPageReq(req);
        List<CourseSchedule> courseSchedules = courseScheduleMapper.listAll();
        if(CollectionUtils.isEmpty(courseSchedules)) {
            return resList;
        }
        Set<String> courseIds = courseSchedules.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
        Set<String> employeeIds = courseSchedules.stream().map(CourseSchedule::getEmployeeId).collect(Collectors.toSet());
        List<Course> courseList = courseMapper.getByIds(courseIds);
        List<Employee> employeeList = employeeMapper.getByIds(employeeIds);

        courseSchedules.forEach(courseSchedule -> {
            ScheduleRes res = new ScheduleRes();
            BeanUtils.copyProperties(courseSchedule, res);
            res.setCourseScheduleId(courseSchedule.getId());

            courseList.forEach(course -> {
                if(course.getId().equals(courseSchedule.getCourseId())) {
                    res.setCourseName(course.getName());
                }
            });
            employeeList.forEach(employee -> {
                if(employee.getId().equals(courseSchedule.getEmployeeId())) {
                    res.setCourseName(employee.getName());
                }
            });
            resList.add(res);
        });
        return resList;
    }

}
