package com.xcjy.web.service;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.bean.StmanagerStudent;
import com.xcjy.web.bean.StudentMoney;
import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.StudentPayType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.CounselorStudentCreateReq;
import com.xcjy.web.controller.req.StmanagerStudentCreateReq;
import com.xcjy.web.controller.req.StudentPayReq;
import com.xcjy.web.mapper.CounselorStudentMapper;
import com.xcjy.web.mapper.StmanagerStudentMapper;
import com.xcjy.web.mapper.StudentMoneyMapper;
import com.xcjy.web.mapper.StudentPayLogMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/25.
 */
@Service
public class RelationService {

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    @Transactional
    public void counselorStudent(CounselorStudentCreateReq req) {
        String employeeId = req.getEmployeeId();

        List<CounselorStudent> counselorStudents = counselorStudentMapper.getByStudentIds(req.getStudentId());

        if (CollectionUtils.isNotEmpty(counselorStudents)) {
            throw new EducationException("存在已经被分配的学生");
        }
        List<CounselorStudent> counselorStudentList = new ArrayList<>();
        for (String studentId : req.getStudentId()) {
            CounselorStudent counselorStudent = new CounselorStudent();
            counselorStudent.setEmployeeId(employeeId);
            counselorStudent.setStudentId(studentId);
            counselorStudent.setMoney(0);
            counselorStudent.setStatus(CounselorStudentStatusType.CONNECTION_NO);
            counselorStudentList.add(counselorStudent);
        }
        counselorStudentMapper.insertBatch(counselorStudentList);
    }

    @Transactional
    public void stmanagerStudent(StmanagerStudentCreateReq req) {
        String schoolId = req.getSchoolId();
        String employeeId = req.getEmployeeId();

        List<CounselorStudent> counselorStudents = counselorStudentMapper.getByEIdStuIdScId(schoolId, employeeId, req.getStudentId());

        if (counselorStudents.size() != req.getStudentId().size()) {
            throw new EducationException("分配的学生中包含非法学生!");
        }

        List<StmanagerStudent> stmanagerStudents = stmanagerStudentMapper.getBySIdAndScIds(schoolId, req.getStudentId());

        if (CollectionUtils.isNotEmpty(stmanagerStudents)) {
            throw new EducationException("存在已经被分配的学生");
        }

        stmanagerStudents = new ArrayList<>();
        for (String studentId : req.getStudentId()) {
            StmanagerStudent stmanagerStudent = new StmanagerStudent();
            stmanagerStudent.setSchoolId(schoolId);
            stmanagerStudent.setEmployeeId(employeeId);
            stmanagerStudent.setStudentId(studentId);
            stmanagerStudent.setRenewMoney(0);
            stmanagerStudent.setBackMoney(0);
            stmanagerStudents.add(stmanagerStudent);
        }
        stmanagerStudentMapper.insertBatch(stmanagerStudents);
    }

}
