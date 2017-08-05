package com.xcjy.web.common.model;

import com.xcjy.web.common.enums.UserType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class UserModel {

    private String id;

    private String schoolId;

    private String name;

    private UserType userType;

    private String entityId;

    private String phone;

    private String username;

    private String roleId;

}
