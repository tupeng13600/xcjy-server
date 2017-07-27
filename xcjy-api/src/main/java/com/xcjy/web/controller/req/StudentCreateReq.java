package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class StudentCreateReq {

    private String schoolId;

    private String name;

    private String idCard;

    private SexType sex;

    private String orignSchool;

    private String grade;

    private Date birthday;

    private String subject;

    private String source;

    private String phone;

    private String parentName;

    private String parentSex;

    private String parentIdCard;

    private String parentPhone;

    private String address;

    private String remark;

    private PayStatusType alreadyPaid = PayStatusType.NO;

}
