package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.SexType;
import lombok.Data;

/**
 * Created by tupeng on 2017/8/15.
 */
@Data
public class StudentShowRes {

    private String id;

    private String schoolName;

    private String name;

    private String idCard;

    private SexType sex;

    private String orignSchool;

    private String grade;

    private String address;

    private String subject;

    private String phone;

    private String remark;

}
