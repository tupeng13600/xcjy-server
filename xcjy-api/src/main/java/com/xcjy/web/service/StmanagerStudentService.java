package com.xcjy.web.service;

import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.enums.StudentPayType;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.StmanagerStatReq;
import com.xcjy.web.controller.req.TeacherScheduleStatReq;
import com.xcjy.web.controller.res.EmployeeMoneyStatRes;
import com.xcjy.web.controller.res.PayStatModel;
import com.xcjy.web.controller.res.StmanagerStatRes;
import com.xcjy.web.controller.res.StudentPayLogStat;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.StmanagerStudentMapper;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.mapper.StudentPayLogMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/8/11.
 */
@Service
public class StmanagerStudentService {

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StudentMapper studentMapper;

    public List<StmanagerStatRes> getStmanagerBack(TeacherScheduleStatReq req) {
        List<StmanagerStatRes> statResList = new ArrayList<>();
        List<String> idList = studentPayLogMapper.getIdsByStartAndEnd(req.getStartTime(), req.getEndTime());
        if (CollectionUtils.isNotEmpty(idList)) {
            List<PayStatModel> payList = studentPayLogMapper.getStatByIds(new HashSet<>(idList), StudentPayType.STUDENTMANAGER_PAY);
            List<PayStatModel> backList = studentPayLogMapper.getStatByIds(new HashSet<>(idList), StudentPayType.STUDENTMANAGER_BACK);
            List<Employee> employeeList = employeeMapper.getByIds(new HashSet<>(idList));
            idList.forEach(id -> statResList.add(getRes(id, payList, backList, employeeList)));
        }
        return statResList;
    }

    private StmanagerStatRes getRes(String employeeId,
                                    List<PayStatModel> payList,
                                    List<PayStatModel> backList,
                                    List<Employee> employeeList) {
        StmanagerStatRes statRes = new StmanagerStatRes();
        statRes.setTeacherId(employeeId);
        for (PayStatModel payStatModel : payList) {
            if (employeeId.equals(payStatModel.getEmployeeId())) {
                statRes.setRenewMoney(payStatModel.getTotalMoney());
            }
        }
        for (PayStatModel backModel : backList) {
            if (employeeId.equals(backModel.getEmployeeId())) {
                statRes.setBackMoney(backModel.getTotalMoney());
            }
        }

        for (Employee employee : employeeList) {
            if (employeeId.equals(employee.getId())) {
                statRes.setTeacherName(employee.getName());
                statRes.setTeacherPhone(employee.getPhone());
            }
        }
        return statRes;
    }

    public EmployeeMoneyStatRes getStat(StmanagerStatReq req) {
        EmployeeMoneyStatRes statRes = new EmployeeMoneyStatRes();
        List<StudentPayLog> payLogs = studentPayLogMapper.getByEmployeeId(CurrentUserUtil.currentEmployeeId(), req.getStartTime(), req.getEndTime());
        if (CollectionUtils.isNotEmpty(payLogs)) {
            Set<String> studentIds = payLogs.stream().map(StudentPayLog::getStudentId).collect(Collectors.toSet());
            List<Student> students = studentMapper.getByIds(studentIds);
            Integer totalMoney = 0;
            Integer totalBack = 0;
            List<StudentPayLogStat> payLogStatList = new ArrayList<>();
            for (StudentPayLog payLog : payLogs) {
                if (StudentPayType.STUDENTMANAGER_PAY.equals(payLog.getOpPayType())) {
                    totalMoney += payLog.getMoney();
                } else if (StudentPayType.STUDENTMANAGER_BACK.equals(payLog.getOpPayType())) {
                    totalBack += payLog.getMoney();
                }
                payLogStatList.add(getPayLogStat(payLog, students, CurrentUserUtil.currentName()));
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
        if (StudentPayType.STUDENTMANAGER_PAY.equals(payLog.getOpPayType())) {
            payLogStat.setMoney(payLog.getMoney());
        } else if (StudentPayType.STUDENTMANAGER_BACK.equals(payLog.getOpPayType())) {
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

}
