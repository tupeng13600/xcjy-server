package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/14.
 */
@Data
public class CounselorStudentRes {

    private String id;

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

    private SexType parentSex;

    private String parentIdCard;

    private String parentPhone;

    private String address;

    private String remark;

    private CounselorStudentStatusType status;

    private Integer money;

}
