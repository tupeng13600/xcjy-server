package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.StudentPayType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/12.
 */
@Service
public class FinanceService {

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    public void studentPay(StudentPayReq req) {
        if (StudentPayType.COUNSELOR_PAY.equals(req.getPayType())) {
            CounselorStudent counselorStudent = counselorStudentMapper.getCS(req.getSchoolId(), req.getEmployeeId(), req.getStudentId());
            if (null == counselorStudent) {
                throw new EducationException("学生未分配给改咨询师");
            }
            counselorStudent.setMoney(counselorStudent.getMoney() + req.getMoney());
            counselorStudent.setUpdateTime(new Date());
            counselorStudent.setStatus(CounselorStudentStatusType.HAS_PAY);
            counselorStudentMapper.updateMoney(counselorStudent);
        } else if (StudentPayType.STUDENTMANAGER_PAY.equals(req.getPayType())) {
            StmanagerStudent stmanagerStudent = stmanagerStudentMapper.getBySES(req.getSchoolId(), req.getEmployeeId(), req.getStudentId());
            if (null == stmanagerStudent) {
                throw new EducationException("学生未分配给改学管师");
            }
            stmanagerStudent.setRenewMoney(stmanagerStudent.getRenewMoney() + req.getMoney());
            Date infoTime = stmanagerStudent.getUpdateTime();
            stmanagerStudent.setUpdateTime(new Date());
            stmanagerStudentMapper.updateMoney(stmanagerStudent, infoTime);
        }

        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(req.getSchoolId(), req.getStudentId());

        if (null == studentMoney) {
            studentMoney = new StudentMoney();
            studentMoney.setStudentId(req.getStudentId());
            studentMoney.setSchoolId(req.getSchoolId());
            studentMoney.setHasPay(req.getMoney());
            studentMoney.setHasBack(0);
            studentMoney.setHasUsed(0);
            studentMoney.setTotalHour(0);
            studentMoney.setUsedHour(0);
            studentMoneyMapper.insert(studentMoney);
        } else {
            studentMoney.setHasPay(studentMoney.getHasPay() + req.getMoney());
            studentMoney.setUpdateTime(new Date());
            studentMoneyMapper.updateMoney(studentMoney);
        }
        //添加学生缴费日志
        StudentPayLog studentPayLog = new StudentPayLog();
        studentPayLog.setEmployeeId(req.getEmployeeId());
        studentPayLog.setOpPayType(req.getPayType());
        studentPayLog.setSchoolId(req.getSchoolId());
        studentPayLog.setStudentId(req.getStudentId());
        studentPayLog.setMoney(req.getMoney());
        studentPayLog.setRemark(req.getRemark());
        studentPayLogMapper.insert(studentPayLog);

        Student student = studentMapper.getById(req.getStudentId());
        if(null == student) {
            throw new EducationException("学生信息不存在");
        }
        student.setAlreadyPaid(PayStatusType.YES);
        student.setUpdateTime(new Date());
        studentMapper.update(student);

    }

}
