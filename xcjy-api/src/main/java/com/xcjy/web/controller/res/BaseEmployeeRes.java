package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/9/3.
 */
@Data
public class BaseEmployeeRes {

    private String id;

    private String schoolId;

    private String name;

    private SexType sex;

    private Date birthday;

    private String idCard;

    private String education;

    private String graduationSchool;

    private String specialty;

    private String phone;

    private String email;

    private String clamantName;

    private String clamantPhone;

    private String address;

    private String remark;

    private RoleEnum role;

}
