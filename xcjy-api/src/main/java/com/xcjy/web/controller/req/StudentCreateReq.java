package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.SexType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class StudentCreateReq {

    @NotBlank
    private String name;

    @NotBlank
    @Length(min = 18, max = 18)
    private String idCard;

    @NotNull
    private SexType sex;

    private String orignSchool;

    private String grade;

    private Date birthday;

    private String subject;

    private String source;

    @NotBlank
    private String phone;

    private String parentName;

    private String parentSex;

    private String parentIdCard;

    private String parentPhone;

    private String address;

    private String remark;

}
