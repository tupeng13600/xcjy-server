package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class CourseStudentReq {

    @NotBlank
    private String studentId;

    @Valid
    @NotEmpty
    private List<BuyCourseHourReq> courseList;

}
