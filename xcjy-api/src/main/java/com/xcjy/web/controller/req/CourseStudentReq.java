package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class CourseStudentReq {

    @NotBlank
    private String studentId;
    @NotBlank
    private String courseId;

    @NotNull
    private Integer buyHour;

}
