package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.School;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.StudentPayType;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.CourseScheduleMapper;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.StudentPayLogMapper;
import com.xcjy.web.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/12.
 */
@Service
public class PresidentService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    public PresidentStatRes payStat(Date startTime, Date endTime) {
        PresidentStatRes statRes = new PresidentStatRes();
        List<Employee> employeeList = getEmployeeList(RoleEnum.CONSULTANT, RoleEnum.CONSULTANT_BOSS);
        if (CollectionUtils.isNotEmpty(employeeList)) {
            List<PresidentDetail> details = new ArrayList<>();
            Set<String> employeeIds = employeeList.stream().map(Employee::getId).collect(Collectors.toSet());
            List<StudentPayLog> payLogList = studentPayLogMapper.getTimeAndEIds(startTime, endTime, employeeIds, StudentPayType.COUNSELOR_PAY);
            for (Employee employee : employeeList) {
                details.add(getDetail(employee, payLogList, statRes));
            }
            statRes.setDetails(details);
        }
        return statRes;
    }

    public PresidentStatRes renewStat(Date startTime, Date endTime) {
        PresidentStatRes statRes = new PresidentStatRes();
        List<Employee> employeeList = getEmployeeList(RoleEnum.STUDENTMANAGER);
        if (CollectionUtils.isNotEmpty(employeeList)) {
            Set<String> employeeIds = employeeList.stream().map(Employee::getId).collect(Collectors.toSet());
            List<StudentPayLog> payLogList = studentPayLogMapper.getTimeAndEIds(startTime, endTime, employeeIds, StudentPayType.STUDENTMANAGER_PAY);
            List<PresidentDetail> details = new ArrayList<>();
            for (Employee employee : employeeList) {
                details.add(getDetail(employee, payLogList, statRes));
            }
            statRes.setDetails(details);
        }
        return statRes;
    }

    private PresidentDetail getDetail(Employee employee, List<StudentPayLog> payLogList, PresidentStatRes statRes) {
        PresidentDetail detail = new PresidentDetail();
        detail.setId(employee.getId());
        detail.setName(employee.getName());
        detail.setPhone(employee.getPhone());
        for (StudentPayLog payLog : payLogList) {
            if (employee.getId().equals(payLog.getEmployeeId())) {
                detail.setNum(detail.getNum() + 1);
                detail.setTotal(detail.getTotal() + payLog.getMoney());
                statRes.setTotal(statRes.getTotal() + payLog.getMoney());
                statRes.setNum(statRes.getNum() + 1);
            }
        }
        School school = CacheFactory.idSchools.get(employee.getSchoolId());
        if(null != school) {
            detail.setSchoolId(school.getId());
            detail.setSchoolName(school.getName());
        }
        return detail;
    }

    private List<Employee> getEmployeeList(RoleEnum... roleEnums) {
        if (null != roleEnums || roleEnums.length == 0) {
            return new ArrayList<>();
        }
        List<User> userList = new ArrayList<>();
        for (RoleEnum roleEnum : roleEnums) {
            userList.addAll(userMapper.getListByRole(roleEnum));
        }
        Set<String> employeeIds = CollectionUtils.isEmpty(userList) ? new HashSet<>() : userList.stream().map(User::getEntityId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(employeeIds)) {
            return new ArrayList<>();
        }
        return employeeMapper.getByIds(employeeIds);
    }

    public PresidentTeacherStatRes teacherHourStat(Date startTime, Date endTime) {
        PresidentTeacherStatRes statRes = new PresidentTeacherStatRes();
        List<Employee> employeeList = getEmployeeList(RoleEnum.TEACHER);
        if (CollectionUtils.isNotEmpty(employeeList)) {
            Set<String> employeeIds = employeeList.stream().map(Employee::getId).collect(Collectors.toSet());
            List<CourseScheduleStatModel> finishStat = courseScheduleMapper.getByEmployeeIdsAndTime(employeeIds, startTime, endTime, true);
            List<CourseScheduleStatModel> unFinishStat = courseScheduleMapper.getByEmployeeIdsAndTime(employeeIds, startTime, endTime, false);
            List<PresidentTeacherStatDetail> details = new ArrayList<>();
            for (Employee employee : employeeList) {
                details.add(getTeacherDetail(employee, finishStat, unFinishStat, statRes));
            }
            statRes.setDetails(details);
        }
        return statRes;
    }

    private PresidentTeacherStatDetail getTeacherDetail(Employee employee,
                                                        List<CourseScheduleStatModel> finishStats,
                                                        List<CourseScheduleStatModel> unFinishStats,
                                                        PresidentTeacherStatRes statRes) {
        PresidentTeacherStatDetail detail = new PresidentTeacherStatDetail();
        detail.setId(employee.getId());
        detail.setName(employee.getName());
        detail.setPhone(employee.getPhone());
        for (CourseScheduleStatModel finishStat : finishStats) {
            if (finishStat.getEmployeeId().equals(employee.getId())) {
                detail.setFinishHour(detail.getFinishHour() + finishStat.getTotal());
                detail.setTotalHour(detail.getTotalHour() + finishStat.getTotal());
                statRes.setTotalHour(statRes.getTotalHour() + finishStat.getTotal());
                statRes.setFinishHour(statRes.getFinishHour() + finishStat.getTotal());
            }
        }

        for (CourseScheduleStatModel unFinishStat : unFinishStats) {
            if (unFinishStat.getEmployeeId().equals(employee.getId())) {
                detail.setUnFinishHour(detail.getUnFinishHour() + unFinishStat.getTotal());
                detail.setTotalHour(detail.getTotalHour() + unFinishStat.getTotal());
                statRes.setTotalHour(statRes.getTotalHour() + unFinishStat.getTotal());
                statRes.setUnFinishHour(statRes.getUnFinishHour() + unFinishStat.getTotal());
            }
        }
        School school = CacheFactory.idSchools.get(employee.getSchoolId());
        if(null != school) {
            detail.setSchoolId(school.getId());
            detail.setSchoolName(school.getName());
        }
        return detail;
    }
}
