package com.xcjy.web.service;

import com.xcjy.web.bean.AplnBackMoney;
import com.xcjy.web.bean.ProcessLog;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.ApplicationStatusType;
import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.BackMoneyCreateReq;
import com.xcjy.web.mapper.AplnBackMoneyMapper;
import com.xcjy.web.mapper.ProcessLogMapper;
import com.xcjy.web.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Cache;

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

    /**
     * 创建退费申请
     * @param req
     */
    @Transactional
    public void backMoney(BackMoneyCreateReq req) {

        AplnBackMoney aplnBackMoney = new AplnBackMoney();
        BeanUtils.copyProperties(req, aplnBackMoney);
        UserModel user = CurrentUserUtil.currentUser();
        if(null == user) {
            throw new EducationException("无法获取申请人信息");
        }
        aplnBackMoney.setApplicationUserId(user.getId());
        aplnBackMoney.setApplicationStatus(ApplicationStatusType.AUDITING);
        aplnBackMoneyMapper.insert(aplnBackMoney);

        ProcessLog processLog = new ProcessLog();
        processLog.setApplicationId(aplnBackMoney.getId());
        processLog.setHandlerStatus(HandlerStatusType.WAIT_AUDIT);

        User roleUser = userMapper.getByRole(CacheFactory.backMoneyAuditRoleChain.get(0));
        processLog.setHandlerUserId(roleUser.getId());

        processLog.setType(ProcessLogType.BACK_MONEY);
        processLogMapper.insert(processLog);

    }
}
