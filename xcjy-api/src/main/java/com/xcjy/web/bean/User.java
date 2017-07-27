package com.xcjy.web.bean;

import com.xcjy.web.common.enums.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;

    private String schoolId;

    private String name;

    private UserType userType;

    private String entityId;

    private String phone;

    private String username;

    private String password;

    private String salt;

    private String roleId;

    private String lastLoginIp;

    private Date lastLoginTime;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}