package com.xcjy.web.common.auth;

import com.xcjy.auth.model.UpcLoginSuccessModel;
import com.xcjy.auth.model.UpcUser;
import com.xcjy.auth.service.AuthMessageService;
import com.xcjy.auth.util.UserUtil;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.controller.res.RoleRes;
import com.xcjy.web.service.RoleService;
import com.xcjy.web.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/18.
 */
public class AuthService implements AuthMessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UpcUser getUser(String username) {
        User user = userService.getByUsernameOrPhone(username, username);
        return null == user ? null : new UpcUser(username, user.getPassword(), user.getSalt());
    }

    @Override
    public Set<String> getRole(String username) {
//        User user = userService.getByUsernameOrPhone(username, username);
//        if (null != user && StringUtils.isNotBlank(user.getRoleId())) {
//            Set<RoleEnum> roleSet = roleService.getRoleByIds(Arrays.asList(user.getRoleId().split(",")));
//            if (CollectionUtils.isNotEmpty(roleSet)) {
//                return roleSet.stream().map(RoleEnum::name).collect(Collectors.toSet());
//            }
//        }
//        return new HashSet<>();
        return getAllRoles(); // TODO: 2017/8/15 暂时使用，获取所有权限，方便调试
    }

    @Override
    public void saveUserMessage(UpcLoginSuccessModel model) {
        userService.updateLoginMessage(UserUtil.getCurrentUserName(), model.getLoginTime(), model.getLoginIp());
    }

    private Set<String> getAllRoles(){
        Set<String> result = new HashSet<>();
        RoleEnum.getAll().forEach(roleEnum -> result.add(roleEnum.name()));
        return result;
    }
}
