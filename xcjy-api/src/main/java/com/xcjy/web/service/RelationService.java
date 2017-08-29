package com.xcjy.web.service;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.bean.StmanagerStudent;
import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.CounselorStudentCreateReq;
import com.xcjy.web.controller.req.StmanagerStudentCreateReq;
import com.xcjy.web.mapper.CounselorStudentMapper;
import com.xcjy.web.mapper.StmanagerStudentMapper;
import com.xcjy.web.mapper.StudentMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private StudentMapper studentMapper;

    @Transactional
    public void counselorStudent(CounselorStudentCreateReq req) {
        String employeeId = req.getEmployeeId();
        UserModel userModel = CacheFactory.empIdUsers.get(employeeId);
        if(null == userModel) {
            throw new EducationException("用户信息不存在");
        }
        Set<RoleEnum> roleEnumSet = CurrentUserUtil.getRoleEnums(userModel.getRoleId());
        if(!roleEnumSet.contains(RoleEnum.CONSULTANT_BOSS) && !roleEnumSet.contains(RoleEnum.CONSULTANT)) {
            throw new EducationException("该员工不是咨询师或者咨询主任");
        }
        List<CounselorStudent> counselorStudents = counselorStudentMapper.getByStudentIds(req.getStudentId());

        if (CollectionUtils.isNotEmpty(counselorStudents)) {
            throw new EducationException("存在已经被分配的学生");
        }
        List<CounselorStudent> counselorStudentList = new ArrayList<>();
        for (String studentId : req.getStudentId()) {
            CounselorStudent counselorStudent = new CounselorStudent();
            counselorStudent.setSchoolId(userModel.getSchoolId());
            counselorStudent.setEmployeeId(employeeId);
            counselorStudent.setStudentId(studentId);
            counselorStudent.setMoney(0);
            counselorStudent.setStatus(CounselorStudentStatusType.CONNECTION_NO);
            counselorStudentList.add(counselorStudent);
        }
        studentMapper.updateDisTributeType(req.getStudentId(), userModel.getSchoolId(), DistributionTypeEnum.COUNSELOR_DISTRIBUTION, new Date());
        counselorStudentMapper.insertBatch(counselorStudentList);
    }

    @Transactional
    public void stmanagerStudent(StmanagerStudentCreateReq req) {
        String employeeId = req.getEmployeeId();
        UserModel userModel = CacheFactory.empIdUsers.get(employeeId);
        if(null == userModel) {
            throw new EducationException("用户信息不存在");
        }
        Set<RoleEnum> roleEnumSet = CurrentUserUtil.getRoleEnums(userModel.getRoleId());
        if(!roleEnumSet.contains(RoleEnum.STUDENTMANAGER)) {
            throw new EducationException("该员工不是学管师");
        }
        List<StmanagerStudent> stmanagerStudents = stmanagerStudentMapper.getBySIdAndScIds(CurrentThreadLocal.getSchoolId(), req.getStudentId());
        if (CollectionUtils.isNotEmpty(stmanagerStudents)) {
            throw new EducationException("存在已经被分配的学生");
        }
        stmanagerStudents = new ArrayList<>();
        for (String studentId : req.getStudentId()) {
            StmanagerStudent stmanagerStudent = new StmanagerStudent();
            stmanagerStudent.setSchoolId(CurrentThreadLocal.getSchoolId());
            stmanagerStudent.setEmployeeId(employeeId);
            stmanagerStudent.setStudentId(studentId);
            stmanagerStudent.setRenewMoney(0);
            stmanagerStudent.setBackMoney(0);
            stmanagerStudents.add(stmanagerStudent);
        }
        stmanagerStudentMapper.insertBatch(stmanagerStudents);
        studentMapper.updateDisTributeType(req.getStudentId(), userModel.getSchoolId(), DistributionTypeEnum.STMANAGER_DISTRIBUTION, new Date());
    }

}
