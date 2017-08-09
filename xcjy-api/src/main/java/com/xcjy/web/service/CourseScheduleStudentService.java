package com.xcjy.web.service;

import com.xcjy.web.bean.CourseSchedule;
import com.xcjy.web.bean.CourseScheduleStudent;
import com.xcjy.web.bean.CourseStudent;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.StudentCourseScheduleReq;
import com.xcjy.web.mapper.CourseScheduleMapper;
import com.xcjy.web.mapper.CourseScheduleStudentMapper;
import com.xcjy.web.mapper.CourseStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/10.
 */
@Service
public class CourseScheduleStudentService {

    @Autowired
    private CourseScheduleStudentMapper courseScheduleStudentMapper;

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;


    public void createSchedule(StudentCourseScheduleReq req) {
        CourseSchedule courseSchedule = courseScheduleMapper.getById(req.getCourseScheduleId());
        if(null == courseSchedule) {
            throw new EducationException("课表信息不存在");
        }
        CourseStudent courseStudent = courseStudentMapper.getBySIdAndCId(req.getStudentId(), courseSchedule.getCourseId());
        if(null == courseStudent) {
            throw new EducationException("学生尚未订购该课程");
        }
        CourseScheduleStudent courseScheduleStudent = new CourseScheduleStudent();
        courseScheduleStudent.setCourseScheduleId(req.getCourseScheduleId());
        courseScheduleStudent.setStudentId(req.getStudentId());
        courseScheduleStudent.setFinish(false);
        courseScheduleStudentMapper.insert(courseScheduleStudent);
        //增加学生已经使用的课时
        Integer usedHour = courseStudent.getUsedHour() + courseStudent.getUsedHour();
        if(courseStudent.getBuyHour() < usedHour) {
            throw new EducationException("该学生的剩余课时不足");
        }
        courseStudent.setUsedHour(usedHour);
        courseStudent.setUpdateTime(new Date());
        courseStudentMapper.updateHour(courseStudent);
    }
}
