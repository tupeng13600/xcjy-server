package com.xcjy.web.service;

import com.xcjy.auth.util.UpcSecurityUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.CurrentThreadLocal;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.controller.req.PageReq;
import com.xcjy.web.controller.req.RegisterReq;
import com.xcjy.web.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateLoginMessage(String currentUserName, Date loginTime, String loginIp) {
        userMapper.updateLoginMessage(currentUserName, loginTime, loginIp, new Date());
    }

    public List<User> getAll(PageReq pageReq) {
        CurrentThreadLocal.setPageReq(pageReq);
        return userMapper.getAll();
    }

}
