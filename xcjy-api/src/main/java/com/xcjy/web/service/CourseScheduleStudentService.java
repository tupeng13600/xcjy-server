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
        if(CollectionUtils.isNotEmpty(courseScheduleStudentList)) {
            Set<String> courseScheduleIds = courseScheduleStudentList.stream().map(CourseScheduleStudent::getCourseScheduleId).collect(Collectors.toSet());
            List<CourseSchedule> courseSchedules = courseScheduleMapper.getByIds(courseScheduleIds);
            Set<String> courseIds = courseSchedules.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
            List<Course> courseList = courseMapper.getByIds(courseIds);
            Set<String> gradeIds = courseList.stream().map(Course::getGradeId).collect(Collectors.toSet());
            List<Grade> gradeList = gradeMapper.getByIds(gradeIds);
            for (CourseScheduleStudent courseScheduleStudent : courseScheduleStudentList) {
                scheduleResList.add(getStudentScheduleRes(courseScheduleStudent, students, courseSchedules, courseList, gradeList));
            }
        }
        return scheduleResList;
    }

    private StudentScheduleRes getStudentScheduleRes(CourseScheduleStudent courseScheduleStudent,
                                                     List<Student> studentList,
                                                     List<CourseSchedule> courseSchedules,
                                                     List<Course> courseList,
                                                     List<Grade> gradeList) {
        StudentScheduleRes res = new StudentScheduleRes();
        res.setFinish(courseScheduleStudent.getFinish());
        res.setCourseScheduleStudentId(courseScheduleStudent.getId());

        for (Student student : studentList) {
            if (courseScheduleStudent.getStudentId().equals(student.getId())) {
                res.setStudentId(student.getId());
                res.setStudentName(student.getName());
                for (CourseSchedule courseSchedule : courseSchedules) {
                    if (courseSchedule.getId().equals(courseScheduleStudent.getCourseScheduleId())) {
                        res.setCourseScheduleId(courseSchedule.getId());
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
        if (null == courseScheduleStudent) {
            throw new EducationException("学生课表信息不存在");
        }
        courseScheduleStudent.setFinish(true);
        courseScheduleStudent.setUpdateTime(new Date());
        courseScheduleStudentMapper.finish(courseScheduleStudent);
    }

    public void cancel(String courseScheduleId, String studentId) {
        CourseSchedule courseSchedule = courseScheduleMapper.getById(courseScheduleId);
        if (null == courseSchedule) {
            throw new EducationException("课表信息不存在");
        }
        CourseStudent courseStudent = courseStudentMapper.getBySIdAndCId(studentId, courseSchedule.getCourseId());
        courseStudent.setUsedHour(courseStudent.getUsedHour() - courseSchedule.getStudyTime());
        courseStudent.setUpdateTime(new Date());
        //归还课时
        courseStudentMapper.updateUsedHour(courseStudent);
        //删除学生课表关系
        courseScheduleStudentMapper.deleteBySIdAndCSId(studentId, courseScheduleId);
    }
}
