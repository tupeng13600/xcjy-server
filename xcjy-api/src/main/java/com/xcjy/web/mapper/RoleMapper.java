package com.xcjy.web.mapper;

import com.xcjy.web.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {

    int insert(Role record);

    Set<Role> getByRoleIds(@Param("ids") List<String> ids);

    Role getByRoleName(@Param("name") String roleName);
}