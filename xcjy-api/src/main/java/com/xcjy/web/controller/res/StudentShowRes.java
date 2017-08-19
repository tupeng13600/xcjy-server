package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

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
