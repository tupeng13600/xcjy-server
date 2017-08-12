package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.*;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.controller.req.ChangeSchoolReq;
import com.xcjy.web.controller.res.AplnSimpleRes;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.controller.res.ProcessRes;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by tupeng on 2017/8/5.
 */
@Service
public class ApplicationService {

    private static Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private AplnBackMoneyMapper aplnBackMoneyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProcessLogMapper processLogMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private StmanagerStudentMapper stmanagerStudentMapper;

    @Autowired
    private AplnChangeSchoolMapper aplnChangeSchoolMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentPayLogMapper studentPayLogMapper;

    /**
     * 创建退费申请
     *
     * @param req
     */
    @Transactional
    public CreateIdRes backMoney(BackMoneyCreateReq req) {
        AplnBackMoney aplnBackMoney = new AplnBackMoney();
        BeanUtils.copyProperties(req, aplnBackMoney);
        UserModel user = CurrentUserUtil.currentUser();
        if (null == user) {
            throw new EducationException("无法获取申请人信息");
        }
        aplnBackMoney.setApplicationUserId(user.getId());
        aplnBackMoney.setSchoolId(user.getSchoolId());
        aplnBackMoney.setApplicationStatus(ApplicationStatusType.AUDITING);
        aplnBackMoneyMapper.insert(aplnBackMoney);
        createProcessLog(aplnBackMoney.getId(), aplnBackMoney.getSchoolId(), aplnBackMoney.getStudentId(),
                0, CacheFactory.getNextBackMoneyProcess(null), ProcessLogType.BACK_MONEY);
        return new CreateIdRes(aplnBackMoney.getId());
    }

    /**
     * 审核退费申请
     *
     * @param processId
     * @param handlerStatus
     */
    @Transactional
    public void auditBackMoney(String processId, HandlerStatusType handlerStatus, String remark) {
        ProcessLog processLog = updateProcessLog(processId, handlerStatus, ProcessLogType.BACK_MONEY, remark);
        if (HandlerStatusType.AUDIT_SUCCESS.equals(handlerStatus)) {
            RoleEnum roleEnum = CacheFactory.getNextBackMoneyProcess(processLog.getProcessNum());
            if (null == roleEnum) {
                //审核流程完毕 更新申请表状态
                AplnBackMoney aplnBackMoney = updateBackMoney(processLog.getApplicationId(), ApplicationStatusType.AUDIT_SUCCESS);
                //更新学生退款金额
                updateStudentMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
                //更新学管师退款金额
                updateStmanagerBackMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getApplicationUserId(),
                        aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
                //创建退费日志
                createPayLog(processLog, aplnBackMoney.getReturnAmount());
            } else {
                //创建下一个审核流程
                createProcessLog(processLog.getApplicationId(), processLog.getSchoolId(), processLog.getStudentId(),
                        processLog.getProcessNum() + 1, roleEnum, ProcessLogType.BACK_MONEY);
            }
        } else {
            updateBackMoney(processLog.getApplicationId(), ApplicationStatusType.AUDIT_FAIL);
        }
    }

    private void createPayLog(ProcessLog processLog, Integer returnAmount) {
        StudentPayLog studentPayLog = new StudentPayLog();
        studentPayLog.setSchoolId(processLog.getSchoolId());
        studentPayLog.setStudentId(processLog.getStudentId());
        studentPayLog.setMoney(returnAmount);
        studentPayLog.setEmployeeId(studentPayLog.getEmployeeId());
        studentPayLog.setOpPayType(StudentPayType.STUDENTMANAGER_BACK);
        studentPayLogMapper.insert(studentPayLog);
    }

    /**
     * 创建转校申请
     *
     * @param req
     */
    @Transactional
    public CreateIdRes changeSchool(ChangeSchoolReq req) {
        AplnChangeSchool aplnChangeSchool = new AplnChangeSchool();
        BeanUtils.copyProperties(req, aplnChangeSchool);
        UserModel user = CurrentUserUtil.currentUser();
        if (null == user) {
            throw new EducationException("无法获取申请人信息");
        }
        Student student = studentMapper.getById(req.getStudentId());
        if (null == student) {
            throw new EducationException("学生信息不存在");
        }
        aplnChangeSchool.setFromSchoolId(student.getSchoolId());
        aplnChangeSchool.setApplicationUserId(user.getId());
        aplnChangeSchool.setApplicationStatus(ApplicationStatusType.AUDITING);
        aplnChangeSchoolMapper.insert(aplnChangeSchool);
        //创建审核流程
        createProcessLog(aplnChangeSchool.getId(), aplnChangeSchool.getFromSchoolId(), aplnChangeSchool.getStudentId(),
                0, CacheFactory.getNextChangeSchoolProcess(null), ProcessLogType.CHANGE_SCHOOL);
        return new CreateIdRes(aplnChangeSchool.getId());
    }

    /**
     * 审核转校申请
     *
     * @param processId
     * @param handlerStatus
     */
    @Transactional
    public void auditChangeSchool(String processId, HandlerStatusType handlerStatus, String remark) {
        ProcessLog processLog = updateProcessLog(processId, handlerStatus, ProcessLogType.CHANGE_SCHOOL, remark);
        if (HandlerStatusType.AUDIT_SUCCESS.equals(handlerStatus)) {
            RoleEnum roleEnum = CacheFactory.getNextBackMoneyProcess(processLog.getProcessNum());
            if (null == roleEnum) {
                //更新转校申请信息
                AplnChangeSchool aplnChangeSchool = updateChangeSchool(processLog.getApplicationId(), ApplicationStatusType.AUDIT_SUCCESS);
                //更新学生所属学校
                updateStudentSchool(aplnChangeSchool.getStudentId(),
                        aplnChangeSchool.getFromSchoolId(), aplnChangeSchool.getToSchoolId());
            } else {
                //创建下一个审核流程
                createProcessLog(processLog.getApplicationId(), processLog.getSchoolId(), processLog.getStudentId(),
                        processLog.getProcessNum() + 1, roleEnum, ProcessLogType.CHANGE_SCHOOL);
            }
        } else {
            updateChangeSchool(processLog.getApplicationId(), ApplicationStatusType.AUDIT_FAIL);
        }

    }

    /**
     * 获取审核流程列表
     *
     * @param handlerStatus
     * @param processLogType
     * @return
     */
    public List<ProcessRes> listProcess(HandlerStatusType handlerStatus, ProcessLogType processLogType) {
        UserModel user = CurrentUserUtil.currentUser();
        List<ProcessLog> processLogs = processLogMapper.getByHandlerUserId(user.getId(), processLogType, handlerStatus);
        if (CollectionUtils.isNotEmpty(processLogs)) {
            Set<String> studentIds = new HashSet<>();
            Set<String> applicationIds = new HashSet<>();
            processLogs.forEach(processLog -> {
                studentIds.add(processLog.getStudentId());
                applicationIds.add(processLog.getApplicationId());
            });
            List<Student> studentList = studentMapper.getByIds(studentIds);
            List<AplnSimpleRes> aplnSimpleRes = getSimpleRes(processLogType, applicationIds);
            return getResult(processLogs, studentList, aplnSimpleRes);
        }
        return new ArrayList<>();
    }

    private List<ProcessRes> getResult(List<ProcessLog> processLogs, List<Student> studentList, List<AplnSimpleRes> aplnSimpleRes) {
        List<ProcessRes> result = new ArrayList<>();
        processLogs.forEach(processLog -> {
            ProcessRes res = new ProcessRes();
            BeanUtils.copyProperties(processLog, res);
            res.setSchoolName(getSchoolName(processLog.getSchoolId()));
            res.setStudentName(getStudentName(studentList, processLog.getStudentId()));
            aplnSimpleRes.forEach(apln -> {
                if (processLog.getApplicationId().equals(apln.getId())) {
                    res.setApplicationRemark(apln.getApplicationRemark());
                    res.setApplicationTime(apln.getApplicationTime());
                    res.setApplicationStatus(apln.getApplicationStatus());
                    res.setReturnAmount(apln.getReturnAmount());
                    res.setToSchoolName(getSchoolName(apln.getToSchoolId()));
                }
            });
            res.setApplicationName(getApplicationName(processLog.getHandlerUserId()));
            result.add(res);
        });
        return result;
    }

    private List<AplnSimpleRes> getSimpleRes(ProcessLogType processLogType, Set<String> applicationIds) {
        List<AplnSimpleRes> aplnSimpleRes;
        if (ProcessLogType.BACK_MONEY.equals(processLogType)) {
            aplnSimpleRes = aplnBackMoneyMapper.getSimpleResByIds(applicationIds);
        } else {
            aplnSimpleRes = aplnChangeSchoolMapper.getSimpleResByIds(applicationIds);
        }
        return aplnSimpleRes;
    }

    private String getApplicationName(String userId) {
        UserModel userModel = CacheFactory.userIdUsers.get(userId);
        if (null == userModel) {
            logger.warn("用户信息不存在 : {}", userId);
            return "-";
        }
        return userModel.getName();
    }

    private String getSchoolName(String schoolId) {
        School school = CacheFactory.idSchools.get(schoolId);
        if (null == school) {
            logger.warn("无法获取学校信息: {}", schoolId);
            return "-";
        } else {
            return school.getName();
        }
    }

    private String getStudentName(List<Student> studentList, String studentId) {
        for (Student student : studentList) {
            if (studentId.equals(student.getId())) {
                return student.getName();
            }
        }
        logger.warn("学生信息不存在：{}", studentId);
        return "-";
    }


    /**
     * 私有方法分界线------------------------------------------------------------------------------------------------------
     */

    @Transactional
    private void updateStudentSchool(String studentId, String fromSchoolId, String toSchoolId) {
        Student student = studentMapper.getById(studentId);
        if (null == student || !fromSchoolId.equals(student.getSchoolId())) {
            throw new EducationException("学生信息不存在");
        }
        student.setSchoolId(toSchoolId);
        student.setUpdateTime(new Date());
        studentMapper.updateSchoolId(student);
    }

    @Transactional
    private AplnChangeSchool updateChangeSchool(String applicationId, ApplicationStatusType applicationStatus) {
        AplnChangeSchool aplnChangeSchool = aplnChangeSchoolMapper.getById(applicationId);
        if (null == aplnChangeSchool) {
            throw new EducationException("申请表中不存在该申请，请确认数据是否正确");
        }
        if (!ApplicationStatusType.AUDITING.equals(aplnChangeSchool.getApplicationStatus())) {
            throw new EducationException("该申请已经在其他时间被审核");
        }
        aplnChangeSchool.setApplicationStatus(applicationStatus);
        aplnChangeSchool.setUpdateTime(new Date());
        aplnChangeSchool.setApplicationTime(new Date());
        aplnChangeSchoolMapper.updateStatus(aplnChangeSchool, ApplicationStatusType.AUDITING);
        return aplnChangeSchool;

    }

    @Transactional
    private void createProcessLog(String applicationId, String schoolId, String studentId, Integer processNum, RoleEnum roleEnum, ProcessLogType processLogType) {
        ProcessLog processLog = new ProcessLog();
        processLog.setApplicationId(applicationId);
        processLog.setHandlerStatus(HandlerStatusType.WAIT_AUDIT);
        List<User> roleUsers = userMapper.getByRole(roleEnum);
        if(CollectionUtils.isEmpty(roleUsers)) {
            throw new EducationException("不存在该角色的用户");
        }
        processLog.setHandlerUserId(roleUsers.get(0).getId());
        processLog.setProcessNum(processNum);
        processLog.setType(processLogType);
        processLog.setSchoolId(schoolId);
        processLog.setStudentId(studentId);
        processLogMapper.insert(processLog);
    }

    @Transactional
    private AplnBackMoney updateBackMoney(String applicationId, ApplicationStatusType applicationStatus) {
        AplnBackMoney aplnBackMoney = aplnBackMoneyMapper.getById(applicationId);
        if (null == aplnBackMoney) {
            throw new EducationException("申请表中不存在该申请，请确认数据是否正确");
        }
        if (!ApplicationStatusType.AUDITING.equals(aplnBackMoney.getApplicationStatus())) {
            throw new EducationException("该申请已经在其他时间被审核");
        }
        aplnBackMoney.setApplicationStatus(applicationStatus);
        aplnBackMoney.setApplicationTime(new Date());
        aplnBackMoney.setUpdateTime(new Date());
        aplnBackMoneyMapper.updateStatus(aplnBackMoney, ApplicationStatusType.AUDITING);
        return aplnBackMoney;
    }

    @Transactional
    private ProcessLog updateProcessLog(String processId, HandlerStatusType handlerStatus, ProcessLogType processLogType, String remark) {
        ProcessLog processLog = processLogMapper.getById(processId);

        if (null == processLog || !processLogType.equals(processLog.getType())) {
            throw new EducationException("流程审核数据不存在或者错误的流程审核类型");
        }

        if (!HandlerStatusType.WAIT_AUDIT.equals(processLog.getHandlerStatus())) {
            throw new EducationException("该审核已经被审核");
        }

        processLog.setHandlerStatus(handlerStatus);
        processLog.setHandlerTime(new Date());
        processLog.setUpdateTime(new Date());
        processLog.setRemark(remark);
        processLogMapper.updateHandler(processLog, HandlerStatusType.WAIT_AUDIT);
        return processLog;
    }


    @Transactional
    private void updateStmanagerBackMoney(String schoolId, String applicationUserId, String studentId, Integer moneyAmount) {

        UserModel userModel = CacheFactory.userIdUsers.get(applicationUserId);
        if (null == userModel) {
            throw new EducationException("不存在该用户");
        }
        StmanagerStudent stmanagerStudent = stmanagerStudentMapper.getBySES(schoolId, userModel.getEntityId(), studentId);
        if (null == stmanagerStudent) {
            throw new EducationException("不存在该管理师学生关系");
        }
        stmanagerStudent.setBackMoney(stmanagerStudent.getBackMoney() + moneyAmount);
        Date infoTime = stmanagerStudent.getUpdateTime();
        stmanagerStudent.setUpdateTime(new Date());
        stmanagerStudentMapper.updateMoney(stmanagerStudent, infoTime);
    }

    @Transactional
    private void updateStudentMoney(String schoolId, String studentId, Integer moneyAmount) {
        StudentMoney studentMoney = studentMoneyMapper.getBySchoolIdAndStudentId(schoolId, studentId);
        if (null == studentMoney) {
            throw new EducationException("学生金额信息不存在");
        }
        studentMoney.setHasBack(studentMoney.getHasBack() + moneyAmount);
        Date infoTime = studentMoney.getUpdateTime();
        studentMoney.setUpdateTime(new Date());
        studentMoneyMapper.updateMoney(studentMoney, infoTime);
    }

}
