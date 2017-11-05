package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.BuyCourseHourReq;
import com.xcjy.web.controller.req.CourseStudentReq;
import com.xcjy.web.controller.res.CounselorStuStatusRes;
import com.xcjy.web.controller.res.CourseStudentStatRes;
import com.xcjy.web.controller.res.StudentInRes;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private CourseScheduleStudentMapper courseScheduleStudentMapper;


    @Transactional
    public void buyCourse(CourseStudentReq req) {
        List<BuyCourseHourReq> courseList = req.getCourseList();
        for (BuyCourseHourReq courseReq : courseList) {
            Course course = courseMapper.getById(courseReq.getCourseId());
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
            validatePay(student.getSchoolId(), student.getId(), course.getPrice() * courseReq.getBuyHour());
            //创建学生课程关系
            createCourseStudent(req.getStudentId(), courseReq.getCourseId(), courseReq.getBuyHour());

            studentMoney.setHasUsed(studentMoney.getHasUsed() + (course.getPrice() * courseReq.getBuyHour()));
            studentMoney.setTotalHour(studentMoney.getTotalHour() + courseReq.getBuyHour());
            studentMoneyMapper.updateMoney(studentMoney);

        }
    }

    private void createCourseStudent(String studentId, String courseId, Integer buyHour) {
        CourseStudent courseStudent = courseStudentMapper.getBySIdAndCId(studentId, courseId);
        if (null == courseStudent) {
            courseStudent = new CourseStudent();
            courseStudent.setStudentId(studentId);
            courseStudent.setCourseId(courseId);
            courseStudent.setSchoolId(CurrentThreadLocal.getSchoolId());
            courseStudent.setUsedHour(0);
            courseStudent.setBuyHour(buyHour);
            courseStudentMapper.insert(courseStudent);
        } else {
            courseStudent.setBuyHour(courseStudent.getBuyHour() + buyHour);
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
                CurrentThreadLocal.ignoreDeleted();
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
        res.setScore(courseStudent.getScore());
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

    public List<StudentInRes> getByCourseId(String courseId, String courseScheduleId) {
        List<CourseStudent> courseStudents = courseStudentMapper.getByCourseId(courseId);
        if (CollectionUtils.isEmpty(courseStudents)) {
            return new ArrayList<>();
        }
        Set<String> studentIds = courseStudents.stream().map(CourseStudent::getStudentId).collect(Collectors.toSet());

        List<StudentInRes> studentList = studentMapper.getInResByIds(studentIds);

        if (StringUtils.isNotEmpty(courseScheduleId)) {
            List<String> inStudentIds = courseScheduleStudentMapper.getStuIdByCSId(courseScheduleId);
            if (CollectionUtils.isNotEmpty(inStudentIds)) {
                studentList.forEach(student -> {
                    if (inStudentIds.contains(student.getId())) {
                        student.setInCourse(true);
                    }
                });
            }
        }
        return studentList;

    }

    @Transactional
    public void courseBack(String studentId, String courseId, Integer hourNum) {
        CourseStudent courseStudent = courseStudentMapper.getBySIdAndCId(studentId, courseId);
        if(null == courseStudent) {
            throw new EducationException("学生尚未购买该课时");
        }
        courseStudent.setBuyHour(courseStudent.getBuyHour() - hourNum);
        courseStudent.setUpdateTime(new Date());
        courseStudentMapper.updateBuyHour(courseStudent);
        StudentMoney studentMoney = studentMoneyMapper.getByStudentId(studentId);
        if (null == studentMoney) {
            throw new EducationException("未找到学生缴费信息");
        }
        Course course = courseMapper.getById(courseId);
        if(null == course) {
            throw new EducationException("课程信息不存在");
        }
        studentMoney.setHasUsed(studentMoney.getHasUsed() - (hourNum * course.getPrice()));
        studentMoney.setTotalHour(studentMoney.getTotalHour() - hourNum);
        studentMoney.setUpdateTime(new Date());
        studentMoneyMapper.updateMoney(studentMoney);
    }
}
