package com.xcjy.web.bean;

import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

@Data
public class Employee {
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

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}