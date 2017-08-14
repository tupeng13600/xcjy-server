package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.School;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.controller.res.StudentPayLogDetailRes;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.mapper.StudentPayLogMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/13.
 */
@Service
public class StudentPayLogService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentPayLogMapper studentPayLogMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    public List<StudentPayLogDetailRes> getPayLogDetailRes(String schoolId) {
        List<StudentPayLogDetailRes> detailResList = new ArrayList<>();
        List<StudentPayLog> payLogList = studentPayLogMapper.getBySchoolId(schoolId);
        if (CollectionUtils.isNotEmpty(payLogList)) {
            Set<String> studentIds = payLogList.stream().map(StudentPayLog::getStudentId).collect(Collectors.toSet());
            Set<String> employeeIds = payLogList.stream().map(StudentPayLog::getEmployeeId).collect(Collectors.toSet());
            List<Employee> employeeList = employeeMapper.getByIds(employeeIds);
            List<Student> studentList = studentMapper.getByIds(studentIds);
            for (StudentPayLog studentPayLog : payLogList) {
                detailResList.add(getRes(studentPayLog, employeeList, studentList));
            }
        }
        return detailResList;
    }

    private StudentPayLogDetailRes getRes(StudentPayLog studentPayLog,
                                          List<Employee> employeeList,
                                          List<Student> studentList) {
        StudentPayLogDetailRes res = new StudentPayLogDetailRes();
        res.setPayType(studentPayLog.getOpPayType());
        res.setMoney(studentPayLog.getMoney());
        res.setLogId(studentPayLog.getId());
        School school = CacheFactory.idSchools.get(studentPayLog.getSchoolId());
        if (null != school) {
            res.setSchoolId(school.getId());
            res.setSchoolName(school.getName());
        }
        for (Employee employee : employeeList) {
            if (employee.getId().equals(studentPayLog.getEmployeeId())) {
                res.setEmployeeId(employee.getId());
                res.setEmployeeName(employee.getName());
                break;
            }
        }
        for (Student student : studentList) {
            if (student.getId().equals(studentPayLog.getStudentId())) {
                res.setStudentId(student.getId());
                res.setStudentName(student.getName());
                break;
            }
        }
        return res;
    }
}
