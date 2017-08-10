package com.xcjy.web.service;

import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.CounselorStatReq;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.res.CounselorStatRes;
import com.xcjy.web.controller.res.StudentPayLogStat;
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
 * Created by tupeng on 2017/8/10.
 */
@Service
public class StudentAssetService {

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private StudentMapper studentMapper;

    public CounselorStatRes getCounselorStat(CounselorStatReq req, PageReq page) {
        CounselorStatRes statRes = new CounselorStatRes();
        Set<RoleEnum> roleEnums = CurrentUserUtil.currentRoles();
        String employeeId = CurrentUserUtil.currentEmployeeId();
        if (!roleEnums.contains(RoleEnum.CONSULTANT) && roleEnums.contains(RoleEnum.CONSULTANT_BOSS)) {
            throw new EducationException("您不是咨询师或者咨询主任");
        }
        CurrentThreadLocal.setPageReq(page);
        List<StudentPayLog> payLogs = studentPayLogMapper.getByEmployeeId(employeeId, req.getStartTime(), req.getEndTime());
        if (CollectionUtils.isNotEmpty(payLogs)) {
            Set<String> studentIds = payLogs.stream().map(StudentPayLog::getStudentId).collect(Collectors.toSet());
            List<Student> students = studentMapper.getByIds(studentIds);
            Integer totalMoney = 0;
            List<StudentPayLogStat> payLogStatList = new ArrayList<>();
            for (StudentPayLog payLog : payLogs) {
                totalMoney += payLog.getMoney();
                payLogStatList.add(getPayLogStat(payLog, students));
            }
            statRes.setDetail(payLogStatList);
            statRes.setTotalMoney(totalMoney);
        }
        return statRes;
    }

    private StudentPayLogStat getPayLogStat(StudentPayLog payLog, List<Student> students) {
        StudentPayLogStat payLogStat = new StudentPayLogStat();
        payLogStat.setEmployeeId(payLog.getEmployeeId());
        payLogStat.setEmployeeName(CurrentUserUtil.currentName());
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
}
