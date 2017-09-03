package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.School;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.StudentPayType;
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

    public EmployeeMoneyStatRes getCounselorStat(CounselorStatReq req) {
        EmployeeMoneyStatRes statRes = new EmployeeMoneyStatRes();
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
            Integer totalBack = 0;
            List<StudentPayLogStat> payLogStatList = new ArrayList<>();
            for (StudentPayLog payLog : payLogs) {
                if (StudentPayType.COUNSELOR_PAY.equals(payLog.getOpPayType())) {
                    totalMoney += payLog.getMoney();
                } else if (StudentPayType.COUNSELOR_BACK.equals(payLog.getOpPayType())) {
                    totalBack += payLog.getMoney();
                }
                payLogStatList.add(getPayLogStat(payLog, students, employeeName));
            }
            statRes.setDetail(payLogStatList);
            statRes.setTotalMoney(totalMoney);
            statRes.setTotalBack(totalBack);
        }
        return statRes;
    }

    private StudentPayLogStat getPayLogStat(StudentPayLog payLog, List<Student> students, String employeeName) {
        StudentPayLogStat payLogStat = new StudentPayLogStat();
        payLogStat.setEmployeeId(payLog.getEmployeeId());
        payLogStat.setEmployeeName(employeeName);
        if (StudentPayType.COUNSELOR_PAY.equals(payLog.getOpPayType())) {
            payLogStat.setMoney(payLog.getMoney());
        } else if (StudentPayType.COUNSELOR_BACK.equals(payLog.getOpPayType())) {
            payLogStat.setHasBack(payLog.getMoney());
        }
        payLogStat.setOpPayType(payLog.getOpPayType());
        payLogStat.setRemark(payLog.getRemark());
        payLogStat.setPayTime(payLog.getCreateTime());
        payLogStat.setStudentId(payLog.getStudentId());
        for (Student student : students) {
            if (student.getId().equals(payLog.getStudentId())) {
                payLogStat.setStudentName(student.getName());
                break;
            }
        }
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
            List<PayStatModel> payModelList = studentPayLogMapper.getStatModelByEmpIds(employeeIds, StudentPayType.COUNSELOR_PAY);
            List<PayStatModel> backModelList = studentPayLogMapper.getStatModelByEmpIds(employeeIds, StudentPayType.COUNSELOR_BACK);
            employeeList.forEach(employee -> signResList.add(getSign(employee, payModelList, counModels, backModelList)));
            signResList.sort(Comparator.comparing(CounselorAssesSignRes::getTotalMoney));
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

    private CounselorAssesSignRes getSign(Employee employee, List<PayStatModel> payModelList, List<CounselorStuNumModel> countModels, List<PayStatModel> backModelList) {
        CounselorAssesSignRes signRes = new CounselorAssesSignRes();
        signRes.setEmployeeId(employee.getId());
        signRes.setName(employee.getName());
        signRes.setPhone(employee.getPhone());
        payModelList.forEach(statModel -> {
            if (employee.getId().equals(statModel.getEmployeeId())) {
                signRes.setSignNum(statModel.getSignNum());
                signRes.setTotalMoney(statModel.getTotalMoney());
            }
        });
        backModelList.forEach(statModel -> {
            if (employee.getId().equals(statModel.getEmployeeId())) {
                signRes.setTotalBack(statModel.getTotalMoney());
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
