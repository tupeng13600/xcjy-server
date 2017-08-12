package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.CourseStudent;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentMoney;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseStudentReq;
import com.xcjy.web.mapper.CourseMapper;
import com.xcjy.web.mapper.CourseStudentMapper;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.mapper.StudentMoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/10.
 */
@Service
public class CourseStudentService {

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;


    @Transactional
    public void buyCourse(CourseStudentReq req) {
        Course course = courseMapper.getById(req.getCourseId());
        if(null == course) {
            throw new EducationException("课程信息不存在");
        }
        Student student = studentMapper.getById(req.getStudentId());
        if(null == student) {
            throw new EducationException("学生信息不存在");
        }
        if(PayStatusType.NO.equals(student.getAlreadyPaid())) {
            throw new EducationException("学生尚未缴费，无法订购课时");
        }
        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(student.getSchoolId(), student.getId());
        //校验学生是否有足够的余额购买课程
        validatePay(student.getSchoolId(), student.getId(), course.getPrice() * req.getBuyHour());
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourseId(req.getCourseId());
        courseStudent.setSchoolId(req.getStudentId());
        courseStudent.setUsedHour(0);
        courseStudent.setBuyHour(req.getBuyHour());
        courseStudentMapper.insert(courseStudent);
        studentMoney.setHasUsed(studentMoney.getHasUsed() + (course.getPrice() * req.getBuyHour()));
        studentMoney.setTotalHour(studentMoney.getTotalHour() + req.getBuyHour());
        studentMoneyMapper.updateMoney(studentMoney, new Date());
    }

    @Transactional
    private StudentMoney validatePay(String schoolId, String studentId, Integer price){
        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(schoolId, studentId);
        if(null == studentMoney) {
            throw new EducationException("未找到学生缴费信息，无法订购课时");
        }
        Integer canUserMoney = studentMoney.getHasPay() - studentMoney.getHasBack() - studentMoney.getHasUsed();
        if(canUserMoney.intValue() < price.intValue()) {
            throw new EducationException("该学生余额不足");
        }
        return studentMoney;
    }

}
