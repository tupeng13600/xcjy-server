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
     * @param processId
     * @param handlerStatus
     */
    @Transactional
    public void auditBackMoney(String processId, HandlerStatusType handlerStatus) {
        ProcessLog processLog = updateProcessLog(processId, handlerStatus);
        if (HandlerStatusType.AUDIT_SUCCESS.equals(handlerStatus)) {
            RoleEnum roleEnum = CacheFactory.getNextBackMoneyProcess(processLog.getProcessNum());
            if (null == roleEnum) {
                //审核流程完毕 更新申请表状态
                AplnBackMoney aplnBackMoney = updateBackMoney(processLog.getApplicationId());
                //更新学生退款金额
                updateStudentMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
                //更新学管师退款金额
                updateStmanagerBackMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getApplicationUserId(),
                        aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
            } else {
                //创建下一个审核流程
                createProcessLog(processLog.getApplicationId(), processLog.getProcessNum() + 1, roleEnum, ProcessLogType.BACK_MONEY);
            }
        }
    }

    /**
     * 创建转校申请
     * @param req
     */
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
     * 私有方法分界线------------------------------------------------------------------------------------------------------
     */

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
    private AplnBackMoney updateBackMoney(String applicationId) {
        AplnBackMoney aplnBackMoney = aplnBackMoneyMapper.getById(applicationId);
        if (!ApplicationStatusType.AUDITING.equals(aplnBackMoney.getApplicationStatus())) {
            throw new EducationException("该申请已经在其他时间被审核");
        }
        if (null == aplnBackMoney) {
            throw new EducationException("申请表中不存在该申请，请确认数据是否正确");
        }
        aplnBackMoney.setApplicationStatus(ApplicationStatusType.AUDIT_SUCCESS);
        aplnBackMoney.setApplicationTime(new Date());
        aplnBackMoney.setUpdateTime(new Date());
        aplnBackMoneyMapper.updateStatus(aplnBackMoney, ApplicationStatusType.AUDITING);
        return aplnBackMoney;
    }

    @Transactional
    private ProcessLog updateProcessLog(String processId, HandlerStatusType handlerStatus) {
        ProcessLog processLog = processLogMapper.getById(processId);
        if (!HandlerStatusType.WAIT_AUDIT.equals(processLog.getHandlerStatus())) {
            throw new EducationException("该审核已经被审核");
        }
        if (null == processId) {
            throw new EducationException("审核流程不存在");
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
