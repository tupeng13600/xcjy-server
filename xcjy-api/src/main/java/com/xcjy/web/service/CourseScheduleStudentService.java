package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.res.StudentScheduleRes;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GradeMapper gradeMapper;

    public List<StudentScheduleRes> getForStmanager() {
        List<StudentScheduleRes> scheduleResList = new ArrayList<>();
        List<String> studentIds = stmanagerStudentMapper.getSIdByEmployeeId(CurrentUserUtil.currentEmployeeId());
        if (CollectionUtils.isEmpty(studentIds)) {
            return scheduleResList;
        }
        List<Student> students = studentMapper.getByIds(new HashSet<>(studentIds));
        List<CourseScheduleStudent> courseScheduleStudentList = courseScheduleStudentMapper.getByStudentIds(studentIds);
        Set<String> courseScheduleIds = courseScheduleStudentList.stream().map(CourseScheduleStudent::getCourseScheduleId).collect(Collectors.toSet());
        List<CourseSchedule> courseSchedules = courseScheduleMapper.getByIds(courseScheduleIds);
        Set<String> courseIds = courseSchedules.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
        List<Course> courseList = courseMapper.getByIds(courseIds);
        Set<String> gradeIds = courseList.stream().map(Course::getGradeId).collect(Collectors.toSet());
        List<Grade> gradeList = gradeMapper.getByIds(gradeIds);
        for (Student student : students) {
            scheduleResList.add(getStudentScheduleRes(student, courseScheduleStudentList, courseSchedules, courseList, gradeList));
        }
        return scheduleResList;
    }

    private StudentScheduleRes getStudentScheduleRes(Student student,
                                                     List<CourseScheduleStudent> courseScheduleStudentList,
                                                     List<CourseSchedule> courseSchedules,
                                                     List<Course> courseList,
                                                     List<Grade> gradeList) {
        StudentScheduleRes res = new StudentScheduleRes();
        res.setStudentId(student.getId());
        res.setStudentName(student.getName());
        for (CourseScheduleStudent courseScheduleStudent : courseScheduleStudentList) {
            if (courseScheduleStudent.getStudentId().equals(student.getId())) {
                res.setFinish(courseScheduleStudent.getFinish());
                res.setId(courseScheduleStudent.getId());
                for (CourseSchedule courseSchedule : courseSchedules) {
                    if (courseSchedule.getId().equals(courseScheduleStudent.getCourseScheduleId())) {
                        res.setStartTime(courseSchedule.getStartTime());
                        res.setEndTime(courseSchedule.getEndTime());
                        for (Course course : courseList) {
                            if (course.getId().equals(courseSchedule.getCourseId())) {
                                res.setCourseName(course.getName());
                                for (Grade grade : gradeList) {
                                    if (course.getGradeId().equals(grade.getId())) {
                                        res.setGradeName(grade.getName());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return res;
    }

    public void finish(String id) {
        CourseScheduleStudent courseScheduleStudent = courseScheduleStudentMapper.getById(id);
        if(null == courseScheduleStudent) {
            throw new EducationException("学生课表信息不存在");
        }
        courseScheduleStudent.setFinish(true);
        courseScheduleStudent.setUpdateTime(new Date());
        courseScheduleStudentMapper.finish(courseScheduleStudent);
    }
}
