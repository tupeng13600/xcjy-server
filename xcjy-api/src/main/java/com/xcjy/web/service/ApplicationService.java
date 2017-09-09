package com.xcjy.web.service;

import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.bean.*;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.*;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.controller.req.ChangeSchoolReq;
import com.xcjy.web.controller.res.*;
import com.xcjy.web.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    /**
     * 创建退费申请
     *
     * @param req
     */
    @Transactional
    public CreateIdRes backMoney(BackMoneyCreateReq req) {
        List<AplnBackMoney> aplnBackMoneyList = aplnBackMoneyMapper.getByStatusAndSId(ApplicationStatusType.AUDITING, req.getStudentId());
        if(CollectionUtils.isNotEmpty(aplnBackMoneyList)) {
            throw new EducationException("该学生存在退费申请未完成，无法创建新的退费申请");
        }
        AplnBackMoney aplnBackMoney = new AplnBackMoney();
        BeanUtils.copyProperties(req, aplnBackMoney);
        UserModel user = CurrentUserUtil.currentUser();
        if (null == user) {
            throw new EducationException("无法获取申请人信息");
        }
        aplnBackMoney.setApplicationUserId(user.getId());
        aplnBackMoney.setSchoolId(user.getSchoolId());
        aplnBackMoney.setApplicationStatus(ApplicationStatusType.AUDITING);
        aplnBackMoney.setApplicationTime(new Date());
        aplnBackMoneyMapper.insert(aplnBackMoney);

        RoleEnum roleEnum = RoleEnum.getByCode(user.getRoleId());
        createProcessLog(aplnBackMoney.getId(), aplnBackMoney.getSchoolId(), aplnBackMoney.getStudentId(),
                0, CacheFactory.getNextBackMoneyProcess(roleEnum, null), ProcessLogType.BACK_MONEY);
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
        AplnBackMoney aplnBackMoney = aplnBackMoneyMapper.getById(processLog.getApplicationId());
        if (null == aplnBackMoney) {
            throw new EducationException("申请表中不存在该申请，请确认数据是否正确");
        }
        if (!ApplicationStatusType.AUDITING.equals(aplnBackMoney.getApplicationStatus())) {
            throw new EducationException("该申请已经在其他时间被审核");
        }
        User user = userMapper.getById(aplnBackMoney.getApplicationUserId());
        if (null == user) {
            throw new EducationException("申请用户信息不存在");
        }
        RoleEnum roleEnum = CacheFactory.getNextBackMoneyProcess(RoleEnum.getByCode(user.getRoleId()), processLog.getProcessNum());
        if (HandlerStatusType.AUDIT_SUCCESS.equals(handlerStatus)) {
            if (null == roleEnum) {
                //审核流程完毕 更新申请表状态
                updateBackMoney(aplnBackMoney, ApplicationStatusType.AUDIT_SUCCESS);
                //更新学生退款金额
                updateStudentMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
                //更新关系表数据
                updateRelationMess(roleEnum, aplnBackMoney);
                //创建退费日志
                createPayLog(processLog, aplnBackMoney.getReturnAmount(), roleEnum);
            } else {
                //创建下一个审核流程
                createProcessLog(processLog.getApplicationId(), processLog.getSchoolId(), processLog.getStudentId(),
                        processLog.getProcessNum() + 1, roleEnum, ProcessLogType.BACK_MONEY);
            }
        } else {
            updateBackMoney(aplnBackMoney, ApplicationStatusType.AUDIT_FAIL);
        }
    }

    /**
     * 更新关系表数据
     *
     * @param roleEnum
     * @param aplnBackMoney
     */
    @Transactional
    private void updateRelationMess(RoleEnum roleEnum, AplnBackMoney aplnBackMoney) {
        if (RoleEnum.CONSULTANT.equals(roleEnum)) {
            //更新咨询师退费金额
            updateCounselorBackMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getApplicationUserId(),
                    aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
        } else if (RoleEnum.STUDENTMANAGER.equals(roleEnum)) {
            //更新学管师退款金额
            updateStmanagerBackMoney(aplnBackMoney.getSchoolId(), aplnBackMoney.getApplicationUserId(),
                    aplnBackMoney.getStudentId(), aplnBackMoney.getReturnAmount());
        } else {
            throw new EducationException("该角色无申请退费的权限");
        }
    }

    @Transactional
    private void updateCounselorBackMoney(String schoolId, String applicationUserId, String studentId, Integer moneyAmount) {
        UserModel userModel = CacheFactory.userIdUsers.get(applicationUserId);
        if (null == userModel) {
            throw new EducationException("不存在该用户");
        }
        CounselorStudent counselorStudent = counselorStudentMapper.getBySES(schoolId, userModel.getEntityId(), studentId);
        if (null == counselorStudent) {
            throw new EducationException("不存在该管理师学生关系");
        }
        counselorStudent.setHasBack(counselorStudent.getHasBack() + moneyAmount);
        counselorStudent.setUpdateTime(new Date());
        counselorStudentMapper.updateMoney(counselorStudent);
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
        aplnChangeSchool.setApplicationTime(new Date());
        aplnChangeSchoolMapper.insert(aplnChangeSchool);
        //创建审核流程
        createProcessLog(aplnChangeSchool.getId(), aplnChangeSchool.getToSchoolId(), aplnChangeSchool.getStudentId(),
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
            RoleEnum roleEnum = CacheFactory.getNextChangeSchoolProcess(processLog.getProcessNum());
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
            CurrentThreadLocal.removeSchoolId();
            List<Student> studentList = studentMapper.getByIds(studentIds);
            List<AplnSimpleRes> aplnSimpleRes = getSimpleRes(processLogType, applicationIds);
            return getResult(processLogs, studentList, aplnSimpleRes);
        }
        return new ArrayList<>();
    }

    /**
     * 私有方法分界线------------------------------------------------------------------------------------------------------
     */

    private List<ProcessRes> getResult(List<ProcessLog> processLogs, List<Student> studentList, List<AplnSimpleRes> aplnSimpleRes) {
        List<ProcessRes> result = new ArrayList<>();
        processLogs.forEach(processLog -> {
            ProcessRes res = new ProcessRes();
            BeanUtils.copyProperties(processLog, res);
            res.setSchoolName(getSchoolName(studentList, processLog.getStudentId()));
            res.setStudentName(getStudentName(studentList, processLog.getStudentId()));
            for (AplnSimpleRes apln : aplnSimpleRes) {
                if (processLog.getApplicationId().equals(apln.getId())) {
                    res.setApplicationRemark(apln.getApplicationRemark());
                    res.setApplicationTime(apln.getApplicationTime());
                    res.setApplicationStatus(apln.getApplicationStatus());
                    res.setReturnAmount(apln.getReturnAmount());
                    res.setToSchoolName(getSchoolName(apln.getToSchoolId()));
                    res.setFromSchoolName(getSchoolName(apln.getFromSchoolId()));
                    break;
                }
            }
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
            String schoolId = CurrentThreadLocal.getSchoolId();
            CurrentThreadLocal.removeSchoolId();
            aplnSimpleRes = aplnChangeSchoolMapper.getSimpleResByIds(applicationIds);
            CurrentThreadLocal.setSchoolId(schoolId);
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

    private String getSchoolName(List<Student> studentList, String studentId) {
        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                School school = CacheFactory.idSchools.get(student.getSchoolId());
                if (null != school) {
                    return school.getName();
                } else {
                    logger.warn("无法获取校区信息，校区ID：{}", student.getSchoolId());
                }
                break;
            }
        }
        return "-";
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

    private void createPayLog(ProcessLog processLog, Integer returnAmount, RoleEnum roleEnum) {
        StudentPayLog studentPayLog = new StudentPayLog();
        studentPayLog.setSchoolId(processLog.getSchoolId());
        studentPayLog.setStudentId(processLog.getStudentId());
        studentPayLog.setMoney(returnAmount);
        studentPayLog.setEmployeeId(studentPayLog.getEmployeeId());
        if (RoleEnum.CONSULTANT.equals(roleEnum)) {
            studentPayLog.setOpPayType(StudentPayType.COUNSELOR_BACK);
        } else if (RoleEnum.STUDENTMANAGER.equals(roleEnum)) {
            studentPayLog.setOpPayType(StudentPayType.STUDENTMANAGER_BACK);
        } else {
            throw new EducationException("该角色无申请退费的权限");
        }
        studentPayLogMapper.insert(studentPayLog);
    }

    @Transactional
    private void updateStudentSchool(String studentId, String fromSchoolId, String toSchoolId) {
        CurrentThreadLocal.setSchoolId(fromSchoolId);
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
        String schoolId = CurrentThreadLocal.getSchoolId();
        CurrentThreadLocal.removeSchoolId();
        AplnChangeSchool aplnChangeSchool = aplnChangeSchoolMapper.getById(applicationId);
        CurrentThreadLocal.setSchoolId(schoolId);
        if (null == aplnChangeSchool) {
            throw new EducationException("申请表中不存在该申请，请确认数据是否正确");
        }
        if (!ApplicationStatusType.AUDITING.equals(aplnChangeSchool.getApplicationStatus())) {
            throw new EducationException("该申请已经在其他时间被审核");
        }
        aplnChangeSchool.setApplicationStatus(applicationStatus);
        aplnChangeSchool.setUpdateTime(new Date());
        aplnChangeSchoolMapper.updateStatus(aplnChangeSchool, ApplicationStatusType.AUDITING);
        return aplnChangeSchool;

    }

    @Transactional
    private void createProcessLog(String applicationId, String schoolId, String studentId, Integer processNum, RoleEnum roleEnum, ProcessLogType processLogType) {
        ProcessLog processLog = new ProcessLog();
        processLog.setApplicationId(applicationId);
        processLog.setHandlerStatus(HandlerStatusType.WAIT_AUDIT);
        processLog.setProcessNum(processNum);
        processLog.setType(processLogType);

        processLog.setStudentId(studentId);
        if (CommonUtil.belongToSchool(roleEnum)) {
            CurrentThreadLocal.setSchoolId(schoolId);
            processLog.setSchoolId(schoolId);
        } else {
            CurrentThreadLocal.removeSchoolId();
        }
        List<User> roleUsers = userMapper.getByRole(roleEnum);
        if (CollectionUtils.isEmpty(roleUsers)) {
            logger.info("学校ID：{} 中未找到对应的角色 {}", schoolId, roleEnum.getName());
            throw new EducationException("未找到对应的角色");
        }
        processLog.setHandlerUserId(roleUsers.get(0).getId());
        processLogMapper.insert(processLog);
    }

    @Transactional
    private void updateBackMoney(AplnBackMoney aplnBackMoney, ApplicationStatusType applicationStatus) {
        aplnBackMoney.setApplicationStatus(applicationStatus);
        aplnBackMoney.setApplicationTime(new Date());
        aplnBackMoney.setUpdateTime(new Date());
        aplnBackMoneyMapper.updateStatus(aplnBackMoney, ApplicationStatusType.AUDITING);
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
        studentMoney.setUpdateTime(new Date());
        studentMoneyMapper.updateMoney(studentMoney);
    }

    public List<Object> getMyProcess(ProcessLogType logType) {
        List<Object> resList = new ArrayList<>();
        if (ProcessLogType.BACK_MONEY.equals(logType)) {
            List<AplnBackMoney> aplnBackMonies = aplnBackMoneyMapper.getByApplicationIds(CurrentUserUtil.currentUserId());
            if (CollectionUtils.isNotEmpty(aplnBackMonies)) {
                Set<String> studentIds = aplnBackMonies.stream().map(AplnBackMoney::getStudentId).collect(Collectors.toSet());
                List<Student> studentList = studentMapper.getByIds(studentIds);
                aplnBackMonies.forEach(aplnBackMoney -> resList.add(getMoneyRes(aplnBackMoney, studentList)));
            }
        } else {
            CurrentThreadLocal.removeSchoolId();
            List<AplnChangeSchool> aplnChangeSchools = aplnChangeSchoolMapper.getByApplicationIds(CurrentUserUtil.currentUserId());
            if (CollectionUtils.isNotEmpty(aplnChangeSchools)) {
                Set<String> studentIds = aplnChangeSchools.stream().map(AplnChangeSchool::getStudentId).collect(Collectors.toSet());
                List<Student> studentList = studentMapper.getByIds(studentIds);
                aplnChangeSchools.forEach(aplnChangeSchool -> resList.add(getChangeSchoolRes(aplnChangeSchool, studentList)));
            }
        }
        return resList;
    }

    private AplnBackMoneyRes getMoneyRes(AplnBackMoney aplnBackMoney, List<Student> studentList) {
        AplnBackMoneyRes res = new AplnBackMoneyRes();
        BeanUtils.copyProperties(aplnBackMoney, res);
        School school = CacheFactory.idSchools.get(aplnBackMoney.getSchoolId());
        if (null != school) {
            res.setSchoolName(school.getName());
        }
        res.setApplicationUser(CurrentUserUtil.currentName());
        for (Student student : studentList) {
            if (student.getId().equals(aplnBackMoney.getStudentId())) {
                res.setStudentId(student.getId());
                res.setStudentName(student.getName());
                break;
            }
        }
        return res;
    }

    private AplnChangeSchoolRes getChangeSchoolRes(AplnChangeSchool aplnChangeSchool, List<Student> studentList) {
        AplnChangeSchoolRes res = new AplnChangeSchoolRes();
        BeanUtils.copyProperties(aplnChangeSchool, res);
        School fromSchool = CacheFactory.idSchools.get(aplnChangeSchool.getFromSchoolId());
        School toSchool = CacheFactory.idSchools.get(aplnChangeSchool.getToSchoolId());
        if (null != fromSchool) {
            res.setFromSchoolName(fromSchool.getName());
        }
        if (null != toSchool) {
            res.setToSchoolName(toSchool.getName());
        }
        res.setApplicationUser(CurrentUserUtil.currentName());
        for (Student student : studentList) {
            if (student.getId().equals(aplnChangeSchool.getStudentId())) {
                res.setStudentId(student.getId());
                res.setStudentName(student.getName());
                break;
            }
        }
        return res;
    }
}
