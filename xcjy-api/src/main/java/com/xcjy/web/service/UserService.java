package com.xcjy.web.service;

import com.xcjy.auth.util.UpcSecurityUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.model.UserModel;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.common.util.CurrentUserUtil;
import com.xcjy.web.controller.req.*;
import com.xcjy.web.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.xcjy.web.common.util.CommonUtil.getRolIdString;

/**
 * Created by tupeng on 2017/7/18.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User getByUsernameOrPhone(String username, String phone) {
        User user = null;
        if (StringUtils.isNotBlank(username)) {
            user = userMapper.getByUsername(username);
        }
        if (null == user && StringUtils.isNotBlank(phone)) {
            user = userMapper.getByPhone(phone);
        }
        return user;
    }

    @Transactional
    public void insert(RegisterReq req) {
        User regUser = new User();
        BeanUtils.copyProperties(req, regUser);
        regUser.setLastLoginIp("0.0.0.0");
        regUser.setLastLoginTime(new Date());
        regUser.setSalt(UpcSecurityUtil.randomString());
        regUser.setPassword(UpcSecurityUtil.encryptPwd(req.getPassword(), new SimpleByteSource(regUser.getSalt())));
        regUser.setRoleId(getRolIdString(req.getRoleIds()));
        userMapper.insert(regUser);
    }

    @Transactional
    public void updateLoginMessage(String currentUserName, Date loginTime, String loginIp) {
        userMapper.updateLoginMessage(currentUserName, loginTime, loginIp, new Date());
    }

    public List<User> getAll(PageReq pageReq) {
        CurrentThreadLocal.setPageReq(pageReq);
        return userMapper.getAll();
    }

    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Transactional
    public void updatePassword(UserBaseUpdateReq req) {

        User user = userMapper.getById(req.getId());

        if (null == user) {
            throw new EducationException("用户不存在");
        }

        if (!req.getPassword().equals(req.getRePassword())) {
            throw new EducationException("两次密码输入不一致");
        }
        user.setSalt(UpcSecurityUtil.randomString());
        user.setPassword(UpcSecurityUtil.encryptPwd(req.getPassword(), new SimpleByteSource(user.getSalt())));
        user.setUpdateTime(new Date());
        userMapper.updatePassword(user);
    }

    @Transactional
    public void updateRole(UserRoleUpdateReq req) {
        User user = userMapper.getById(req.getId());
        if (null == user) {
            throw new EducationException("用户信息不存在");
        }
        user.setRoleId(CommonUtil.getRolIdString(req.getRoleList()));
        user.setUpdateTime(new Date());
        userMapper.updateRole(user);
    }

    public void updateSelfPassword(UserPwdSelfUpdateReq req) {
        UserModel userModel = CurrentUserUtil.currentUser();
        User user = userMapper.getById(userModel.getId());
        if (null == user) {
            throw new EducationException("用户信息不存在");
        }
        user.setSalt(UpcSecurityUtil.randomString());
        user.setPassword(UpcSecurityUtil.encryptPwd(req.getPassword(), new SimpleByteSource(user.getSalt())));
        user.setUpdateTime(new Date());
        userMapper.updatePassword(user);
    }
}
