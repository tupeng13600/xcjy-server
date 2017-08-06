package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class EmployeeCreateReq {

    @NotBlank
    private String username;

    @NotEmpty
    private List<RoleEnum> roleIds;

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
