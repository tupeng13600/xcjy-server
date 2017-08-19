package com.xcjy.web.service;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.bean.School;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentMoney;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.common.util.DateUtil;
import com.xcjy.web.controller.req.StudentCreateReq;
import com.xcjy.web.controller.req.StudentUpdateReq;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.CounselorStudentMapper;
import com.xcjy.web.mapper.StmanagerStudentMapper;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.mapper.StudentMoneyMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Transactional
    public CreateIdRes create(StudentCreateReq req) {
        Student student = studentMapper.getByIdCard(req.getIdCard());
        if (null != student) {
            throw new EducationException("学生身份证号已经存在");
        }
        student = new Student();
        BeanUtils.copyProperties(req, student);
        if (null == student.getBirthday()) {
            student.setBirthday(DateUtil.getBirthByIdCard(student.getIdCard()));
        }
        student.setAlreadyPaid(PayStatusType.NO);
        student.setDistributionType(DistributionTypeEnum.COUNSELOR_DISTRIBUTION);
        student.setSchoolId(CurrentThreadLocal.getSchoolId());
        studentMapper.insert(student);
        CounselorStudent counselorStudent = new CounselorStudent();
        counselorStudent.setSchoolId(CurrentThreadLocal.getSchoolId());
        counselorStudent.setStatus(CounselorStudentStatusType.CONNECTION_NO);
        counselorStudent.setEmployeeId(CurrentUserUtil.currentEmployeeId());
        counselorStudent.setMoney(0);
        counselorStudent.setStudentId(student.getId());
        counselorStudentMapper.insert(counselorStudent);
        return new CreateIdRes(student.getId());
    }

    @Transactional
    public void update(StudentUpdateReq req) {
        Student student = studentMapper.getById(req.getId());
        if (null == student) {
            throw new EducationException("学生信息不存在");
        }
        validateIdCard(req.getIdCard(), student.getIdCard());
        BeanUtils.copyProperties(req, student);
        student.setBirthday(DateUtil.getBirthByIdCard(student.getIdCard()));
        studentMapper.update(student);
    }

    private void validateIdCard(String idCard, String repoIdCard) {
        if (!idCard.equals(repoIdCard)) {
            if (null != studentMapper.getByIdCard(idCard)) {
                throw new EducationException("身份证号码已经被使用");
            }
        }
    }

    public void deleteLogic(String id) {
        studentMapper.deleteLogic(id, new Date());
    }

    public List<CounselorStudentRes> list4Counselor() {
        List<CounselorStudent> counselorStudentList = counselorStudentMapper.getByEmployeeId(CurrentUserUtil.currentEmployeeId());
        if (CollectionUtils.isEmpty(counselorStudentList)) {
            return new ArrayList<>();
        }
        Set<String> studentIds = counselorStudentList.stream().map(CounselorStudent::getStudentId).collect(Collectors.toSet());
        List<Student> studentList = studentMapper.getByIds(studentIds);
        return getResList(counselorStudentList, studentList);
    }

    private List<CounselorStudentRes> getResList(List<CounselorStudent> counselorStudentList, List<Student> studentList) {
        List<CounselorStudentRes> resList = new ArrayList<>();
        studentList.forEach(student -> {
            CounselorStudentRes res = new CounselorStudentRes();
            BeanUtils.copyProperties(student, res);
            for (CounselorStudent counselorStudent : counselorStudentList) {
                if (counselorStudent.getStudentId().equals(student.getId())) {
                    res.setStatus(counselorStudent.getStatus());
                    res.setMoney(counselorStudent.getMoney());
                    break;
                }
            }
            resList.add(res);
        });
        return resList;
    }

    public List<StudentAssetsRes> getAssets() {
        List<StudentAssetsRes> resList = new ArrayList<>();
        Set<RoleEnum> roleEnums = CurrentUserUtil.currentRoles();
        String employeeId = CurrentUserUtil.currentEmployeeId();
        List<String> studentIds = null;
        //咨询师或者咨询主任
        if (roleEnums.contains(RoleEnum.CONSULTANT) || roleEnums.contains(RoleEnum.CONSULTANT_BOSS)) {
            studentIds = counselorStudentMapper.getSIdByEmployeeId(employeeId);
        } else if (roleEnums.contains(RoleEnum.STUDENTMANAGER)) {
            studentIds = stmanagerStudentMapper.getSIdByEmployeeId(employeeId);
        }
        if (CollectionUtils.isNotEmpty(studentIds)) {
            List<Student> students = studentMapper.getByIds(new HashSet<>(studentIds));
            List<StudentMoney> studentMonies = studentMoneyMapper.getByStudentIds(new HashSet<>(studentIds));
            studentMonies.forEach(studentMoney -> {
                StudentAssetsRes assetsRes = new StudentAssetsRes();
                BeanUtils.copyProperties(studentMoney, assetsRes);
                students.forEach(student -> {
                    if (student.getId().equals(studentMoney.getStudentId())) {
                        BeanUtils.copyProperties(student, assetsRes);
                    }
                });
                resList.add(assetsRes);
            });
        }
        return resList;
    }

    public List<Student> getForStmanager() {
        List<String> studentIds = stmanagerStudentMapper.getSIdByEmployeeId(CurrentUserUtil.currentEmployeeId());
        if (CollectionUtils.isEmpty(studentIds)) {
            return new ArrayList<>();
        }
        return studentMapper.getByIds(new HashSet<>(studentIds));
    }

    public List<StudentShowRes> getList4ByDisType(DistributionTypeEnum distributionType) {
        List<StudentShowRes> showResList = new ArrayList<>();
        List<Student> studentList = studentMapper.getByDisType(distributionType);
        if (CollectionUtils.isNotEmpty(studentList)) {
            studentList.forEach(student -> {
                StudentShowRes showRes = new StudentShowRes();
                BeanUtils.copyProperties(student, showRes);
                if (StringUtils.isNotBlank(student.getSchoolId())) {
                    School school = CacheFactory.idSchools.get(student.getSchoolId());
                    if (null != school) {
                        showRes.setSchoolName(school.getName());
                    }
                }
                showResList.add(showRes);
            });
        }
        return showResList;
    }

    public List<FinanceStudentStatRes> getBySchoolId(String schoolId) {
        List<FinanceStudentStatRes> statResList = new ArrayList<>();
        CurrentThreadLocal.setSchoolId(schoolId);
        List<Student> studentList = studentMapper.getAll();
        if (CollectionUtils.isNotEmpty(studentList)) {
            Set<String> studentIds = studentList.stream().map(Student::getId).collect(Collectors.toSet());
            List<StudentMoney> studentMonies = studentMoneyMapper.getByStudentIds(studentIds);
            for (Student student : studentList) {
                statResList.add(getRes(student, studentMonies));
            }
        }
        return statResList;
    }

    private FinanceStudentStatRes getRes(Student student, List<StudentMoney> studentMonies) {
        FinanceStudentStatRes statRes = new FinanceStudentStatRes();
        BeanUtils.copyProperties(student, statRes);
        School school = CacheFactory.idSchools.get(student.getSchoolId());
        if (null != school) {
            statRes.setSchoolName(school.getName());
        }
        for (StudentMoney studentMony : studentMonies) {
            if (studentMony.getStudentId().equals(student.getId())) {
                statRes.setHasPay(studentMony.getHasPay());
                statRes.setHasBack(studentMony.getHasBack());
                statRes.setHasUsed(studentMony.getHasUsed());
                break;
            }
        }
        return statRes;
    }

}
