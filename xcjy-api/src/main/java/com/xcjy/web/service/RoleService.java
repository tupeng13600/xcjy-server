package com.xcjy.web.service;

import com.xcjy.web.bean.Role;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.RoleCreateReq;
import com.xcjy.web.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Set<Role> getRoleByIds(List<String> roleIds) {
        return roleMapper.getByRoleIds(roleIds);
    }

    public void create(RoleCreateReq req) {
        Role role = roleMapper.getByRoleName(req.getRoleName());
        if (null != role) {
            throw new EducationException("角色名称已存在");
        }
        role = new Role();
        role.setName(req.getRoleName());
        role.setRemark(req.getRemark());
        roleMapper.insert(role);
    }
}
