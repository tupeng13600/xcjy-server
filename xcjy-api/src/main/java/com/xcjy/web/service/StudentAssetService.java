package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.School;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.AssetsSignReq;
import com.xcjy.web.controller.req.CounselorStatReq;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/10.
 */
@Service
public class StudentAssetService {

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Autowired
    private UserMapper userMapper;

    public CounselorStatRes getCounselorStat(CounselorStatReq req) {
        CounselorStatRes statRes = new CounselorStatRes();
        Set<RoleEnum> roleEnums = CurrentUserUtil.currentRoles();
        if (!roleEnums.contains(RoleEnum.CONSULTANT) && !roleEnums.contains(RoleEnum.CONSULTANT_BOSS)) {
            throw new EducationException("您不是咨询师或者咨询主任");
        }

        String employeeId = CurrentUserUtil.currentEmployeeId();
        String employeeName = CurrentUserUtil.currentName();
        if (roleEnums.contains(RoleEnum.CONSULTANT_MAIN) && StringUtils.isNotBlank(req.getEmployeeId())) {
            employeeId = req.getEmployeeId();
            Employee employee = employeeMapper.getById(employeeId);
            employeeName = employee.getName();
        }
        List<StudentPayLog> payLogs = studentPayLogMapper.getByEmployeeId(employeeId, req.getStartTime(), req.getEndTime());
        if (CollectionUtils.isNotEmpty(payLogs)) {
            Set<String> studentIds = payLogs.stream().map(StudentPayLog::getStudentId).collect(Collectors.toSet());
            List<Student> students = studentMapper.getByIds(studentIds);
            Integer totalMoney = 0;
            List<StudentPayLogStat> payLogStatList = new ArrayList<>();
            for (StudentPayLog payLog : payLogs) {
                totalMoney += payLog.getMoney();
                payLogStatList.add(getPayLogStat(payLog, students, employeeName));
            }
            statRes.setDetail(payLogStatList);
            statRes.setTotalMoney(totalMoney);
        }
        return statRes;
    }

    private StudentPayLogStat getPayLogStat(StudentPayLog payLog, List<Student> students, String employeeName) {
        StudentPayLogStat payLogStat = new StudentPayLogStat();
        payLogStat.setEmployeeId(payLog.getEmployeeId());
        payLogStat.setEmployeeName(employeeName);
        payLogStat.setMoney(payLog.getMoney());
        payLogStat.setOpPayType(payLog.getOpPayType());
        payLogStat.setRemark(payLog.getRemark());
        payLogStat.setPayTime(payLog.getCreateTime());
        payLogStat.setStudentId(payLog.getStudentId());
        students.forEach(student -> {
            if (student.getId().equals(payLog.getStudentId())) {
                payLogStat.setStudentName(student.getName());
            }
        });
        return payLogStat;
    }

    public List<CounselorAssesSignRes> getAssetsSign(AssetsSignReq req) {
        List<CounselorAssesSignRes> signResList = new ArrayList<>();
        List<Employee> employeeList = null;
        if (StringUtils.isNotBlank(req.getEmployeeId())) {
            employeeList = new ArrayList<>();
            employeeList.add(employeeMapper.getById(req.getEmployeeId()));
        } else {
            Set<String> employeeIds = getEmployeeIds();
            if (CollectionUtils.isNotEmpty(employeeIds)) {
                employeeList = employeeMapper.getByIds(employeeIds);
            }
        }
        if (CollectionUtils.isNotEmpty(employeeList)) {
            Set<String> employeeIds = employeeList.stream().map(Employee::getId).collect(Collectors.toSet());

            List<CounselorStuNumModel> counModels = counselorStudentMapper.getStudentNumByEIds(employeeIds);
            List<PayStatModel> statModelList = studentPayLogMapper.getStatModelByEmpIds(employeeIds);
            employeeList.forEach(employee -> {
                signResList.add(getSign(employee, statModelList, counModels));
            });
            Collections.sort(signResList, Comparator.comparing(CounselorAssesSignRes::getTotalMoney));
        }
        return signResList;
    }

    private Set<String> getEmployeeIds() {
        Set<String> employeeIds = new HashSet<>();
        List<String> des1 = userMapper.getBySchoolId(null, RoleEnum.CONSULTANT);
        if (CollectionUtils.isNotEmpty(des1)) {
            employeeIds.addAll(des1);
        }
        List<String> des2 = userMapper.getBySchoolId(null, RoleEnum.CONSULTANT_BOSS);
        if (CollectionUtils.isNotEmpty(des2)) {
            employeeIds.addAll(des2);
        }
        return employeeIds;
    }

    private CounselorAssesSignRes getSign(Employee employee, List<PayStatModel> statModelList, List<CounselorStuNumModel> countModels) {
        CounselorAssesSignRes signRes = new CounselorAssesSignRes();
        signRes.setEmployeeId(employee.getId());
        signRes.setName(employee.getName());
        signRes.setPhone(employee.getPhone());
        statModelList.forEach(statModel -> {
            if (employee.getId().equals(statModel.getEmployeeId())) {
                signRes.setSignNum(statModel.getSignNum());
                signRes.setTotalMoney(statModel.getTotalMoney());
            }
        });
        countModels.forEach(countModel -> {
            if (countModel.getEmployeeId().equals(employee.getId())) {
                signRes.setTotalStudentNum(countModel.getStudentNum());
            }
        });
        School school = CacheFactory.idSchools.get(employee.getSchoolId());
        if (null != school) {
            signRes.setSchoolId(school.getId());
            signRes.setSchoolName(school.getName());
        }
        return signRes;
    }

}
