package com.xcjy.web.service;

import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.bean.*;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.CourseScheduleCreateReq;
import com.xcjy.web.controller.req.CourseScheduleUpdateReq;
import com.xcjy.web.controller.req.TeacherScheduleStatReq;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
    private CourseTeacherMapper courseTeacherMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private CourseScheduleStudentMapper courseScheduleStudentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 学管师创建课表，选择课表上课时间，上课老师，上课学生等
     *
     * @param req
     * @return
     */
    @Transactional
    public CreateIdRes createStudentSchedule(CourseScheduleCreateReq req) {
        valid(req); //校验数据的正确性
        CourseSchedule courseSchedule = getCourseSchedule(req);
        List<CourseStudent> courseStudents = courseStudentMapper.getBySIdAndCIds(req.getStudentIds(), courseSchedule.getCourseId());
        if (courseStudents.size() != req.getStudentIds().size()) {
            throw new EducationException("存在未订购该课时的学生");
        }
        //删除历史数据，并归还学生使用课时
        backCourseSchedule(courseSchedule);
        //新建学生课表关系数据
        createScheduleStudent(req, courseSchedule);
        //更新学生已经使用的学时数据
        updateCourseStudent(courseStudents, courseSchedule);
        //更新学生金额信息
        studentMoneyMapper.increaseUsedHour(req.getStudentIds(), req.getStudyTime(), new Date());
        return new CreateIdRes(courseSchedule.getId());
    }

    private CourseSchedule getCourseSchedule(CourseScheduleCreateReq req) {
        CourseSchedule courseSchedule;
        if (StringUtils.isNotEmpty(req.getCourseScheduleId())) {
            courseSchedule = courseScheduleMapper.getById(req.getCourseScheduleId());
            if (null == courseSchedule) {
                throw new EducationException("课表ID不存在");
            }
            BeanUtils.copyProperties(req, courseSchedule);
            courseSchedule.setUpdateTime(new Date());
            courseScheduleMapper.updateBase(courseSchedule);
        } else {
            courseSchedule = createCourseSchedule(req);
        }
        return courseSchedule;
    }

    @Transactional
    private void updateCourseStudent(List<CourseStudent> courseStudents, CourseSchedule courseSchedule) {
        for (CourseStudent courseStudent : courseStudents) {
            Integer usedHour = courseSchedule.getStudyTime() + courseStudent.getUsedHour();
            if (courseStudent.getBuyHour() < usedHour) {
                throw new EducationException("该学生该课程的剩余课时不足");
            }
            courseStudent.setUsedHour(usedHour);
            courseStudent.setUpdateTime(new Date());
        }
        courseStudentMapper.updateHourBatch(courseStudents);
    }

    @Transactional
    private void createScheduleStudent(CourseScheduleCreateReq req, CourseSchedule courseSchedule) {
        List<CourseScheduleStudent> cssList = new ArrayList<>();
        for (String studentId : req.getStudentIds()) {
            CourseScheduleStudent courseScheduleStudent = new CourseScheduleStudent();
            courseScheduleStudent.setCourseScheduleId(courseSchedule.getId());
            courseScheduleStudent.setStudentId(studentId);
            courseScheduleStudent.setFinish(false);
            courseScheduleStudent.setSchoolId(CurrentThreadLocal.getSchoolId());
            cssList.add(courseScheduleStudent);
        }

        courseScheduleStudentMapper.insertBatch(cssList);
    }


    @Transactional
    private void backCourseSchedule(CourseSchedule courseSchedule) {
        List<CourseScheduleStudent> courseScheduleStudents = courseScheduleStudentMapper.getByCourseScheduleId(courseSchedule.getId());
        if (CollectionUtils.isNotEmpty(courseScheduleStudents)) {
            Set<String> studentIds = courseScheduleStudents.stream().map(CourseScheduleStudent::getStudentId).collect(Collectors.toSet());
            List<CourseStudent> courseStudents = courseStudentMapper.getBySIdAndCIds(studentIds, courseSchedule.getCourseId());
            for (CourseStudent courseStudent : courseStudents) {
                Integer usedHour = courseStudent.getUsedHour() - courseSchedule.getStudyTime();
                courseStudent.setUsedHour(usedHour);
                courseStudent.setUpdateTime(new Date());
            }
            courseStudentMapper.updateHourBatch(courseStudents);
            Set<String> ids = courseScheduleStudents.stream().map(CourseScheduleStudent::getId).collect(Collectors.toSet());
            courseScheduleStudentMapper.deleteByIds(ids);
            studentMoneyMapper.decreaseUsedHourBatch(studentIds, courseSchedule.getStudyTime(), new Date());
        }
    }

    @Transactional
    private void valid(CourseScheduleCreateReq req) {
        Course course = courseMapper.getById(req.getCourseId());
        if (null == course) {
            throw new EducationException("课程信息不存在");
        }
        Employee employee = employeeMapper.getById(req.getEmployeeId());
        if (null == employee) {
            throw new EducationException("员工信息不存在");
        }
    }

    @Transactional
    private CourseSchedule createCourseSchedule(CourseScheduleCreateReq req) {
        CourseSchedule courseSchedule = new CourseSchedule();
        BeanUtils.copyProperties(req, courseSchedule);
        courseSchedule.setSchoolId(CurrentThreadLocal.getSchoolId());
        courseSchedule.setFinish(false);
        courseScheduleMapper.insert(courseSchedule);
        return courseSchedule;
    }

    @Transactional
    public CourseSchedule update(CourseScheduleUpdateReq req) {
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
        return courseSchedule;
    }

    public List<ScheduleRes> list() {
        List<ScheduleRes> resList = new ArrayList<>();
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
            res.setStudyTime(courseSchedule.getStudyTime());
            courseList.forEach(course -> {
                if (course.getId().equals(courseSchedule.getCourseId())) {
                    res.setCourseName(course.getName());
                    res.setCourseId(course.getId());
                }
            });
            employeeList.forEach(employee -> {
                if (employee.getId().equals(courseSchedule.getEmployeeId())) {
                    res.setTeacherName(employee.getName());
                    res.setTeacherId(employee.getId());
                }
            });
            resList.add(res);
        });
        return resList;
    }

    public List<TeacherScheduleRes> getTeacherSchedule() {
        String teacherId = CurrentUserUtil.currentEmployeeId();
        List<CourseSchedule> courseScheduleList = courseScheduleMapper.getByEmployeeId(teacherId);
        return buildTeacherScheduleList(courseScheduleList);
    }

    private List<TeacherScheduleRes> buildTeacherScheduleList(List<CourseSchedule> courseScheduleList) {
        List<TeacherScheduleRes> scheduleResList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(courseScheduleList)) {
            Set<String> teacherIds = courseScheduleList.stream().map(CourseSchedule::getEmployeeId).collect(Collectors.toSet());
            Set<String> courseIds = courseScheduleList.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
            List<Employee> employeeList = employeeMapper.getByIds(teacherIds);
            List<Course> courseList = courseMapper.getByIds(courseIds);
            courseScheduleList.forEach(courseSchedule -> scheduleResList.add(getRes(courseSchedule, employeeList, courseList)));
        }
        return scheduleResList;
    }

    public List<TeacherScheduleRes> getTeacherSchedule(Boolean finish) {
        List<CourseSchedule> courseScheduleList = courseScheduleMapper.getByFinish(finish);
        return buildTeacherScheduleList(courseScheduleList);
    }

    private TeacherScheduleRes getRes(CourseSchedule courseSchedule, List<Employee> employeeList, List<Course> courseList) {

        TeacherScheduleRes res = new TeacherScheduleRes();
        res.setCourseId(courseSchedule.getCourseId());
        res.setTeacherId(courseSchedule.getEmployeeId());
        res.setStartTime(courseSchedule.getStartTime());
        res.setEndTime(courseSchedule.getEndTime());
        res.setFinish(courseSchedule.getFinish());
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

    public List<TeacherScheduleStatRes> getTSStat(TeacherScheduleStatReq req) {
        List<TeacherScheduleStatRes> scheduleResList = new ArrayList<>();
        List<CourseScheduleStatModel> totalModels = courseScheduleMapper.getByStartEndFinish(req.getStartTime(), req.getEndTime(), null);
        if (CollectionUtils.isNotEmpty(totalModels)) {
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

    public TeacherHourStatRes statHour4Teacher(Date startTime, Date endTime) {
        TeacherHourStatRes res = new TeacherHourStatRes();
        List<CourseSchedule> courseScheduleList = courseScheduleMapper.getByEmployeeIdAndFinish(CurrentUserUtil.currentEmployeeId(), startTime, endTime, true);
        if (CollectionUtils.isNotEmpty(courseScheduleList)) {
            Set<String> courseIds = courseScheduleList.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
            List<Course> courseList = courseMapper.getByIds(courseIds);
            List<TeacherCourseHourStat> details = new ArrayList<>();
            Integer total = 0;
            for (Course course : courseList) {
                TeacherCourseHourStat stat = new TeacherCourseHourStat();
                stat.setCourseName(course.getName());
                stat.setStudyHour(course.getStudyHour());
                for (CourseSchedule courseSchedule : courseScheduleList) {
                    if (courseSchedule.getCourseId().equals(course.getId())) {
                        stat.setHours(stat.getHours() + courseSchedule.getStudyTime());
                        total += courseSchedule.getStudyTime();
                        break;
                    }
                }
                details.add(stat);
            }
            res.setTotalHours(total);
            res.setDetail(details);
        }
        return res;
    }

    @Transactional
    public void finish4Teacher(String scheduleId) {
        CourseSchedule courseSchedule = courseScheduleMapper.getById(scheduleId);
        if (null == courseSchedule) {
            throw new EducationException("课表信息不存在");
        }
        courseSchedule.setFinish(true);
        courseSchedule.setUpdateTime(new Date());
        courseScheduleMapper.updateFinish(courseSchedule);
    }

    public List<Employee> getByCourseId(String courseId) {
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.getByCId(courseId);
        if (CollectionUtils.isNotEmpty(courseTeacherList)) {
            Set<String> employeeIds = courseTeacherList.stream().map(CourseTeacher::getTeacherId).collect(Collectors.toSet());
            return employeeMapper.getByIds(employeeIds);
        }
        return new ArrayList<>();
    }

    public List<TeacherCourseHisRes> getHis4Teacher() {
        List<TeacherCourseHisRes> resultList = new ArrayList<>();
        String employeeId = CurrentUserUtil.currentEmployeeId();
        List<CourseSchedule> courseScheduleList = courseScheduleMapper.getByEmployeeId(employeeId);
        if (CollectionUtils.isNotEmpty(courseScheduleList)) {
            Set<String> courseIds = courseScheduleList.stream().map(CourseSchedule::getCourseId).collect(Collectors.toSet());
            List<Course> courseList = courseMapper.getByIds(courseIds);
            List<Grade> gradeList = null;
            if (CollectionUtils.isNotEmpty(courseList)) {
                Set<String> gradeIds = courseList.stream().map(Course::getGradeId).collect(Collectors.toSet());
                gradeList = gradeMapper.getByIds(gradeIds);
            }
            for (CourseSchedule courseSchedule : courseScheduleList) {
                TeacherCourseHisRes res = new TeacherCourseHisRes();
                res.setCourseId(courseSchedule.getCourseId());
                res.setStartTime(courseSchedule.getStartTime());
                res.setEndTime(courseSchedule.getEndTime());
                res.setFinish(courseSchedule.getFinish());
                res.setStudyTime(courseSchedule.getStudyTime());
                setCourseMess(res, courseList, courseSchedule.getId(), gradeList);
                resultList.add(res);
            }
        }
        return resultList;
    }

    private void setCourseMess(TeacherCourseHisRes res, List<Course> courseList, String courseId, List<Grade> gradeList) {
        if (CollectionUtils.isNotEmpty(courseList)) {
            for (Course course : courseList) {
                if (course.getId().equals(courseId)) {
                    res.setCourseName(course.getName());
                    setGradeMess(res, gradeList, course.getGradeId());
                    break;
                }
            }
        }
    }

    private void setGradeMess(TeacherCourseHisRes res, List<Grade> gradeList, String gradeId) {
        if (CollectionUtils.isNotEmpty(gradeList)) {
            for (Grade grade : gradeList) {
                if (grade.getId().equals(gradeId)) {
                    res.setGradeId(gradeId);
                    res.setGradeName(grade.getName());
                    break;
                }
            }
        }
    }

    public List<StudentScoreRes> getScore4Teacher() {
        List<StudentScoreRes> resultList = new ArrayList<>();
        String employeeId = CurrentUserUtil.currentEmployeeId();
        List<CourseTeacher> courseTeacherList = courseTeacherMapper.getByEmployeeId(employeeId);
        if (CollectionUtils.isNotEmpty(courseTeacherList)) {
            Set<String> courseIds = courseTeacherList.stream().map(CourseTeacher::getCourseId).collect(Collectors.toSet());
            List<CourseStudent> courseStudentList = courseStudentMapper.getByCourseIds(courseIds);
            List<Course> courseList = courseMapper.getByIds(courseIds);
            List<Grade> gradeList = null;
            List<Student> studentList = null;
            if (CollectionUtils.isNotEmpty(courseList)) {
                Set<String> gradeIds = courseList.stream().map(Course::getGradeId).collect(Collectors.toSet());
                gradeList = gradeMapper.getByIds(gradeIds);
            }
            if (CollectionUtils.isNotEmpty(courseStudentList)) {
                Set<String> studentIds = courseStudentList.stream().map(CourseStudent::getStudentId).collect(Collectors.toSet());
                studentList = studentMapper.getByIds(studentIds);
            }
            if (CollectionUtils.isNotEmpty(studentList)) {
                for (Student student : studentList) {
                    StudentScoreRes res = new StudentScoreRes();
                    res.setStudentId(student.getId());
                    res.setStudentName(student.getName());
                    setCourse(res, courseStudentList, courseList, gradeList);
                    resultList.add(res);
                }
            }
        }
        return resultList;
    }

    private void setCourse(StudentScoreRes res,
                           List<CourseStudent> courseStudentList,
                           List<Course> courseList,
                           List<Grade> gradeList) {
        if (CollectionUtils.isNotEmpty(courseStudentList)) {
            for (CourseStudent courseStudent : courseStudentList) {
                if (res.getStudentId().equals(courseStudent.getStudentId())) {
                    res.setHasUsed(courseStudent.getUsedHour());
                    res.setBuyHour(courseStudent.getBuyHour());
                    res.setScore(courseStudent.getScore());
                    for (Course course : courseList) {
                        if (course.getId().equals(courseStudent.getCourseId())) {
                            res.setCourseName(course.getName());
                            res.setStudyHour(course.getStudyHour());
                            if (CollectionUtils.isNotEmpty(gradeList)) {
                                for (Grade grade : gradeList) {
                                    if (grade.getId().equals(course.getGradeId())) {
                                        res.setGradeName(grade.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
