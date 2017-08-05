package com.xcjy.web.service;

import com.xcjy.web.bean.*;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.ApplicationStatusType;
import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.controller.req.ChangeSchoolReq;
import com.xcjy.web.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Service
public class ApplicationService {

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

    /**
     * 创建退费申请
     *
     * @param req
     */
    @Transactional
    public void backMoney(BackMoneyCreateReq req) {
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
        createProcessLog(aplnBackMoney.getId(), 0, CacheFactory.getNextBackMoneyProcess(null), ProcessLogType.BACK_MONEY);
    }

    /**
     * 审核退费申请
     *
     * @param processId
     * @param handlerStatus
     */
    @Transactional
    public void auditBackMoney(String processId, HandlerStatusType handlerStatus) {
        ProcessLog processLog = updateProcessLog(processId, handlerStatus, ProcessLogType.BACK_MONEY);
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
            } else {
                //创建下一个审核流程
                createProcessLog(processLog.getApplicationId(), processLog.getProcessNum() + 1, roleEnum, ProcessLogType.BACK_MONEY);
            }
        } else {
            updateBackMoney(processLog.getApplicationId(), ApplicationStatusType.AUDIT_FAIL);
        }
    }

    /**
     * 创建转校申请
     *
     * @param req
     */
    @Transactional
    public void changeSchool(ChangeSchoolReq req) {
        AplnChangeSchool aplnChangeSchool = new AplnChangeSchool();
        BeanUtils.copyProperties(req, aplnChangeSchool);
        UserModel user = CurrentUserUtil.currentUser();
        if (null == user) {
            throw new EducationException("无法获取申请人信息");
        }
        aplnChangeSchool.setApplicationUserId(user.getId());
        aplnChangeSchool.setApplicationStatus(ApplicationStatusType.AUDITING);
        aplnChangeSchoolMapper.insert(aplnChangeSchool);
        //创建审核流程
        createProcessLog(aplnChangeSchool.getId(), 0, CacheFactory.getNextChangeSchoolProcess(null), ProcessLogType.CHANGE_SCHOOL);
    }

    /**
     * 审核转校申请
     *
     * @param processId
     * @param handlerStatus
     */
    public void auditChangeSchool(String processId, HandlerStatusType handlerStatus) {
        ProcessLog processLog = updateProcessLog(processId, handlerStatus, ProcessLogType.CHANGE_SCHOOL);
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
                createProcessLog(processLog.getApplicationId(), processLog.getProcessNum() + 1, roleEnum, ProcessLogType.CHANGE_SCHOOL);
            }
        } else {
            updateChangeSchool(processLog.getApplicationId(), ApplicationStatusType.AUDIT_FAIL);
        }

    }

    private void updateStudentSchool(String studentId, String fromSchoolId, String toSchoolId) {
        Student student = studentMapper.getById(studentId);
        if (null == student || !fromSchoolId.equals(student.getSchoolId())) {
            throw new EducationException("学生信息不存在");
        }
        student.setSchoolId(toSchoolId);
        student.setUpdateTime(new Date());
        studentMapper.updateSchoolId(student);
    }


    /**
     * 私有方法分界线------------------------------------------------------------------------------------------------------
     */

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
    private void createProcessLog(String applicationId, Integer processNum, RoleEnum roleEnum, ProcessLogType processLogType) {
        ProcessLog processLog = new ProcessLog();
        processLog.setApplicationId(applicationId);
        processLog.setHandlerStatus(HandlerStatusType.WAIT_AUDIT);
        User roleUser = userMapper.getByRole(roleEnum);
        processLog.setHandlerUserId(roleUser.getId());
        processLog.setProcessNum(processNum);
        processLog.setType(processLogType);
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
    private ProcessLog updateProcessLog(String processId, HandlerStatusType handlerStatus, ProcessLogType processLogType) {
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
