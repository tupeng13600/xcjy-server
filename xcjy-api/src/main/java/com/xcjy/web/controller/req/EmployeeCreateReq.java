package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.SexType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class EmployeeCreateReq {

    @NotBlank
    private String username;

    @NotBlank
    private String roleIds;

    private String schoolId;

    @NotBlank
    private String name;

    private SexType sex;

    private Date birthday;

    @NotBlank
    private String idCard;

    private String education;

    private String graduationSchool;

    private String specialty;

    @NotBlank
    private String phone;

    private String email;

    private String clamantName;

    private String clamantPhone;

    private String address;

    private String remark;

}
