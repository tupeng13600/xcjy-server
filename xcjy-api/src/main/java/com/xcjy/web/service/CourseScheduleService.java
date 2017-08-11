package com.xcjy.web.service;

import com.xcjy.web.bean.Course;
import com.xcjy.web.bean.CourseSchedule;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CourseScheduleCreateReq;
import com.xcjy.web.controller.req.CourseScheduleUpdateReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.req.TeacherScheduleStatReq;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.CourseMapper;
import com.xcjy.web.mapper.CourseScheduleMapper;
import com.xcjy.web.mapper.CourseTeacherMapper;
import com.xcjy.web.mapper.EmployeeMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Transactional
    public CreateIdRes create(CourseScheduleCreateReq req) {

        Course course = courseMapper.getById(req.getCourseId());
        if (null == course) {
            throw new EducationException("课程信息不存在");
        }
        Employee employee = employeeMapper.getById(req.getEmployeeId());
        if (null == employee) {
            throw new EducationException("员工信息不存在");
        }
        CourseSchedule courseSchedule = new CourseSchedule();
        BeanUtils.copyProperties(req, courseSchedule);
        courseSchedule.setSchoolId(CurrentThreadLocal.getSchoolId());
        courseSchedule.setFinish(false);
        courseScheduleMapper.insert(courseSchedule);
        return new CreateIdRes(courseSchedule.getId());
    }

    @Transactional
    public void update(CourseScheduleUpdateReq req) {
        CourseSchedule courseSchedule = courseScheduleMapper.getById(req.getId());
        if (null == courseSchedule) {
            throw new EducationException("课表信息不存在");
        }
        if (null == courseMapper.getById(req.getCourseId())) {
            throw new EducationException("课程信息不存在");
        }
        if (null == employeeMapper.getById(req.getEmployeeId())) {
            throw new EducationException("员工信息不存在");
        }
        if (null == courseTeacherMapper.getByCIdAndTId(req.getCourseId(), req.getEmployeeId())) {
            throw new EducationException("该教师无法教授该课程");
        }
        BeanUtils.copyProperties(req, courseSchedule);
        courseScheduleMapper.updateBase(courseSchedule);
    }

    public List<ScheduleRes> list(PageReq req) {
        List<ScheduleRes> resList = new ArrayList<>();
        CurrentThreadLocal.setPageReq(req);
        List<CourseSchedule> courseSchedules = courseScheduleMapper.listAll();
        if (CollectionUtils.isEmpty(courseSchedules)) {
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
                if (course.getId().equals(courseSchedule.getCourseId())) {
                    res.setCourseName(course.getName());
                }
            });
            employeeList.forEach(employee -> {
                if (employee.getId().equals(courseSchedule.getEmployeeId())) {
                    res.setCourseName(employee.getName());
                }
            });
            resList.add(res);
        });
        return resList;
    }

    public List<TeacherScheduleRes> getTeacherSchedule(Boolean finish, PageReq page) {
        List<TeacherScheduleRes> scheduleResList = new ArrayList<>();
        CurrentThreadLocal.setPageReq(page);
        List<CourseSchedule> courseScheduleList = courseScheduleMapper.getByFinish(finish);
        if (CollectionUtils.isNotEmpty(courseScheduleList)) {
            Set<String> teacherIds = courseScheduleList.stream().map(CourseSchedule::getEmployeeId).collect(Collectors.toSet());
            Set<String> courseIds = courseScheduleList.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
            List<Employee> employeeList = employeeMapper.getByIds(teacherIds);
            List<Course> courseList = courseMapper.getByIds(courseIds);
            courseScheduleList.forEach(courseSchedule -> scheduleResList.add(getRes(courseSchedule, employeeList, courseList)));
        }
        return scheduleResList;
    }

    private TeacherScheduleRes getRes(CourseSchedule courseSchedule, List<Employee> employeeList, List<Course> courseList) {

        TeacherScheduleRes res = new TeacherScheduleRes();
        res.setCourseId(courseSchedule.getCourseId());
        res.setTeacherId(courseSchedule.getEmployeeId());
        res.setStartTime(courseSchedule.getStartTime());
        res.setEndTime(courseSchedule.getEndTime());
        for (Employee employee : employeeList) {
            if (employee.getId().equals(res.getTeacherId())) {
                res.setTeacherName(employee.getName());
                break;
            }
        }
        for (Course course : courseList) {
            if (course.getId().equals(res.getCourseId())) {
                res.setCourseName(course.getName());
                break;
            }
        }
        return res;
    }

    public List<TeacherScheduleStatRes> getTSStat(TeacherScheduleStatReq req, PageReq page) {
        List<TeacherScheduleStatRes> scheduleResList = new ArrayList<>();
        CurrentThreadLocal.setPageReq(page);
        List<CourseScheduleStatModel> totalModels = courseScheduleMapper.getByStartEndFinish(req.getStartTime(), req.getEndTime(), null);
        if (CollectionUtils.isNotEmpty(scheduleResList)) {
            Set<String> teacherIds = totalModels.stream().map(CourseScheduleStatModel::getEmployeeId).collect(Collectors.toSet());
            List<CourseScheduleStatModel> finishModels = courseScheduleMapper.getByEmployeeIds(teacherIds, true);
            List<Employee> employeeList = employeeMapper.getByIds(teacherIds);
            for (CourseScheduleStatModel totalModel : totalModels) {
                scheduleResList.add(getRes(totalModel, finishModels, employeeList));
            }
        }
        return scheduleResList;
    }

    private TeacherScheduleStatRes getRes(CourseScheduleStatModel totalModel,
                                          List<CourseScheduleStatModel> finishModels,
                                          List<Employee> employeeList) {
        TeacherScheduleStatRes res = new TeacherScheduleStatRes();
        res.setTotalHour(totalModel.getTotal());
        for (CourseScheduleStatModel finishModel : finishModels) {
            if (finishModel.getEmployeeId().equals(totalModel.getEmployeeId())) {
                res.setFinishHour(finishModel.getTotal());
                break;
            }
        }
        for (Employee employee : employeeList) {
            if (employee.getId().equals(totalModel.getEmployeeId())) {
                res.setTeacherId(employee.getId());
                res.setTeacherName(employee.getName());
                res.setTeacherPhone(employee.getPhone());
                break;
            }
        }
        return res;
    }

}
