package com.xcjy.web.service;

import com.google.common.collect.Lists;
import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.bean.*;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseCreateReq;
import com.xcjy.web.controller.req.CourseUpdateReq;
import com.xcjy.web.controller.res.CourseShowRes;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.mapper.*;
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

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private CourseScheduleStudentMapper courseScheduleStudentMapper;

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

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
        List<CourseStudent> courseStudents = courseStudentMapper.getByCourseId(id);
        backMoney(courseStudents);
    }

    private void backMoney(List<CourseStudent> courseStudents) {
        if (CollectionUtils.isNotEmpty(courseStudents)) {
            Integer needBackMoney = 0;
            Integer totalBackHour = 0;
            for (CourseStudent courseStudent : courseStudents) {
                Integer needBackHours = courseStudent.getBuyHour() - courseStudent.getUsedHour();
                if (needBackHours > 0) {
                    totalBackHour += needBackHours;
                    Course course = courseMapper.getById(courseStudent.getId());
                    if (null != course) {
                        needBackMoney += (needBackHours * course.getPrice());
                    }
                }
                StudentMoney studentMoney = studentMoneyMapper.getByStudentId(courseStudent.getStudentId());
                List<CourseScheduleStudent> courseScheduleStudentList = courseScheduleStudentMapper.getByStudentIds(Lists.newArrayList(courseStudent.getStudentId()));
                if (CollectionUtils.isNotEmpty(courseScheduleStudentList)) {
                    for (CourseScheduleStudent courseScheduleStudent : courseScheduleStudentList) {
                        if (!courseScheduleStudent.getFinish()) {
                            CourseSchedule courseSchedule = courseScheduleMapper.getById(courseScheduleStudent.getCourseScheduleId());
                            if (null != courseSchedule) {
                                Course course = courseMapper.getById(courseSchedule.getId());
                                if (null != course) {
                                    needBackMoney += (course.getPrice() * courseSchedule.getStudyTime());
                                    needBackHours += courseSchedule.getStudyTime();
                                }
                            }
                        }
                    }
                }
                if (null != studentMoney) {
                    studentMoney.setHasBack(needBackMoney);
                    studentMoney.setTotalHour(studentMoney.getTotalHour() - totalBackHour);
                    studentMoneyMapper.updateMoney(studentMoney);
                }

            }

        }
    }

    public List<CourseShowRes> list() {
        List<Course> courseList = courseMapper.getAll();
        List<CourseShowRes> resList = new ArrayList<>();
        List<Grade> gradeList = gradeMapper.getAll();
        if (CollectionUtils.isNotEmpty(courseList)) {
            courseList.forEach(course -> {
                CourseShowRes showRes = new CourseShowRes();
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
