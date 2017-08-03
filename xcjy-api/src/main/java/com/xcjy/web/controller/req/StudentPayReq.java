package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.StudentPayType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by tupeng on 2017/7/25.
 */
@Data
public class StudentPayReq {

    @NotBlank
    private String studentId;

    @NotBlank
    private String schoolId;

    @NotBlank
    private String employeeId;

    @NotNull
    private StudentPayType payType;

    @NotNull
    private Integer money;

    private String remark;

}
