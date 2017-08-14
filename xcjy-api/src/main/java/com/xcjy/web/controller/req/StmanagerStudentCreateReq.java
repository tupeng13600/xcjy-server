package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/25.
 */
@Data
public class StmanagerStudentCreateReq {

    @NotBlank
    private String employeeId;

    @NotEmpty
    private Set<String> studentId;

}
