package com.xcjy.web.service;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.mapper.CounselorStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/21.
 */
@Service
public class CounselorStudentService {

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Transactional
    public void updateCounStu2NoPay(String studentId) {
        String employeeId = CurrentUserUtil.currentEmployeeId();
        CounselorStudent counselorStudent = counselorStudentMapper.getStuIdAndEmpId(employeeId, studentId);
        if(null == counselorStudent) {
            throw new EducationException("非法操作");
        }
        counselorStudent.setStatus(CounselorStudentStatusType.NO_PAY);
        counselorStudent.setUpdateTime(new Date());
        counselorStudentMapper.updaStatus(counselorStudent);
    }
}
