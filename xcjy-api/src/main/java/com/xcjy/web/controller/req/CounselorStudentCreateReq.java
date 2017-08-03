package com.xcjy.web.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by tupeng on 2017/7/25.
 */
@Data
public class CounselorStudentCreateReq {

    @NotNull
    @ApiModelProperty("校区ID")
    private String schoolId;

    @NotBlank
    @ApiModelProperty("员工ID")
    private String employeeId;

    @NotEmpty
    @ApiModelProperty("学生ID列表")
    private Set<String> studentId;

}
