package com.xcjy.web.mapper;

import com.xcjy.web.bean.User;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.UserType;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    int insert(User record);

    void updateLoginMessage(@Param("username") String username,
                            @Param("loginTime") Date loginTime,
                            @Param("loginIp") String loginIp,
                            @Param("updateTime") Date updateTime);

    User getByUsername(@Param("username") String username);

    User getByPhone(@Param("phone") String phone);

    User getByEntityId(@Param("userType") UserType employee, @Param("entityId") String entityId);

    void updateBase(@Param("user") User user);

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);

    List<User> getByRole(@Param("roleCode") RoleEnum roleCode);

    List<User> getAll();

    User getById(@Param("id") String id);

    void updatePassword(@Param("user") User user);

    void updateRole(@Param("user") User user);

    List<String> getBySchoolId(@Param("schoolId") String schoolId, @Param("role") RoleEnum role);

    List<User> getListByRole(@Param("roleCode") RoleEnum roleCode);
}