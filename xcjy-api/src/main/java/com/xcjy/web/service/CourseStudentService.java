package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.CourseStudentReq;
import com.xcjy.web.controller.res.CounselorStuStatusRes;
import com.xcjy.web.controller.res.CourseStudentStatRes;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;


    @Transactional
    public void buyCourse(CourseStudentReq req) {
        Course course = courseMapper.getById(req.getCourseId());
        if (null == course) {
            throw new EducationException("课程信息不存在");
        }
        Student student = studentMapper.getById(req.getStudentId());
        if (null == student) {
            throw new EducationException("学生信息不存在");
        }
        if (PayStatusType.NO.equals(student.getAlreadyPaid())) {
            throw new EducationException("学生尚未缴费，无法订购课时");
        }
        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(student.getSchoolId(), student.getId());
        //校验学生是否有足够的余额购买课程
        validatePay(student.getSchoolId(), student.getId(), course.getPrice() * req.getBuyHour());
        //创建学生课程关系
        createCourseStudent(req);

        studentMoney.setHasUsed(studentMoney.getHasUsed() + (course.getPrice() * req.getBuyHour()));
        studentMoney.setTotalHour(studentMoney.getTotalHour() + req.getBuyHour());
        studentMoneyMapper.updateMoney(studentMoney);
    }

    private void createCourseStudent(CourseStudentReq req) {
        CourseStudent courseStudent = courseStudentMapper.getBySIdAndCId(req.getStudentId(), req.getCourseId());
        if (null == courseStudent) {
            courseStudent = new CourseStudent();
            courseStudent.setStudentId(req.getStudentId());
            courseStudent.setCourseId(req.getCourseId());
            courseStudent.setSchoolId(CurrentThreadLocal.getSchoolId());
            courseStudent.setUsedHour(0);
            courseStudent.setBuyHour(req.getBuyHour());
            courseStudentMapper.insert(courseStudent);
        } else {
            courseStudent.setBuyHour(courseStudent.getBuyHour() + req.getBuyHour());
            courseStudent.setUpdateTime(new Date());
            courseStudentMapper.updateUsedHour(courseStudent);
        }
    }

    @Transactional
    private StudentMoney validatePay(String schoolId, String studentId, Integer price) {
        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(schoolId, studentId);
        if (null == studentMoney) {
            throw new EducationException("未找到学生缴费信息，无法订购课时");
        }
        Integer canUserMoney = studentMoney.getHasPay() - studentMoney.getHasBack() - studentMoney.getHasUsed();
        if (canUserMoney.intValue() < price.intValue()) {
            throw new EducationException("该学生余额不足");
        }
        return studentMoney;
    }

    public List<CounselorStuStatusRes> getCounselorStudentTypeHis(Date startTime, Date endTime) {
        List<CounselorStuStatusRes> resList = new ArrayList<>();
        List<CounselorStudent> counselorStudentList = counselorStudentMapper.getByTime(startTime, endTime);
        if (CollectionUtils.isNotEmpty(counselorStudentList)) {
            Set<String> studentIds = counselorStudentList.stream().map(CounselorStudent::getStudentId).collect(Collectors.toSet());
            Set<String> employeeIds = counselorStudentList.stream().map(CounselorStudent::getEmployeeId).collect(Collectors.toSet());
            List<Student> studentList = studentMapper.getByIds(studentIds);
            List<Employee> employeeList = employeeMapper.getByIds(employeeIds);
            for (CounselorStudent counselorStudent : counselorStudentList) {
                resList.add(getStatusRes(counselorStudent, studentList, employeeList));
            }
        }
        return resList;
    }

    private CounselorStuStatusRes getStatusRes(CounselorStudent counselorStudent,
                                               List<Student> studentList,
                                               List<Employee> employeeList) {
        CounselorStuStatusRes stuStatusRes = new CounselorStuStatusRes();
        stuStatusRes.setStatus(counselorStudent.getStatus());
        stuStatusRes.setUpdateTime(counselorStudent.getUpdateTime());
        for (Student student : studentList) {
            if (student.getId().equals(counselorStudent.getStudentId())) {
                stuStatusRes.setStudentId(student.getId());
                stuStatusRes.setStudentName(student.getName());
                stuStatusRes.setStudentPhone(student.getPhone());
                break;
            }
        }
        for (Employee employee : employeeList) {
            if (employee.getId().equals(counselorStudent.getEmployeeId())) {
                stuStatusRes.setEmployeeId(employee.getId());
                stuStatusRes.setEmployeeName(employee.getName());
                stuStatusRes.setEmployeePhone(employee.getPhone());
                break;
            }
        }
        return stuStatusRes;
    }

    public void updateScore(String courseId, String studentId, Integer score) {
        courseStudentMapper.updateScore(courseId, studentId, score);
    }

    public List<CourseStudentStatRes> stat() {
        String employeeId = CurrentUserUtil.currentEmployeeId();
        List<CourseStudentStatRes> resList = new ArrayList<>();
        List<String> studentIds = stmanagerStudentMapper.getSIdByEmployeeId(employeeId);
        if (CollectionUtils.isNotEmpty(studentIds)) {
            List<CourseStudent> courseStudents = courseStudentMapper.getByStudentIds(studentIds);
            if (CollectionUtils.isNotEmpty(courseStudents)) {
                Set<String> courseIds = courseStudents.stream().map(CourseStudent::getCourseId).collect(Collectors.toSet());
                List<Course> courseList = courseMapper.getByIds(courseIds);
                List<Student> studentList = studentMapper.getByIds(new HashSet<>(studentIds));
                for (CourseStudent courseStudent : courseStudents) {
                    resList.add(getRes(courseStudent, courseList, studentList));
                }
            }
        }
        return resList;
    }

    private CourseStudentStatRes getRes(CourseStudent courseStudent, List<Course> courseList, List<Student> studentList) {
        CourseStudentStatRes res = new CourseStudentStatRes();
        res.setBuyHour(courseStudent.getBuyHour());
        res.setBuyTime(courseStudent.getCreateTime());
        res.setCourseId(courseStudent.getCourseId());
        res.setStudentId(courseStudent.getStudentId());
        res.setUsedHour(courseStudent.getUsedHour());
        for (Course course : courseList) {
            if (course.getId().equals(courseStudent.getCourseId())) {
                res.setCourseName(course.getName());
                break;
            }
        }
        for (Student student : studentList) {
            if (student.getId().equals(courseStudent.getStudentId())) {
                res.setStudentName(student.getName());
                break;
            }
        }
        return res;
    }

    public List<Student> getByCourseId(String courseId) {
        List<CourseStudent> courseStudents = courseStudentMapper.getByCourseId(courseId);
        if (CollectionUtils.isEmpty(courseStudents)) {
            return new ArrayList<>();
        }
        Set<String> studentIds = courseStudents.stream().map(CourseStudent::getStudentId).collect(Collectors.toSet());
        return studentMapper.getByIds(studentIds);
    }
}
