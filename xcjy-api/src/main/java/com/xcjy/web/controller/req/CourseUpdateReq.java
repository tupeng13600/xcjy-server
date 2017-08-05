package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.CourseType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class CourseUpdateReq {

    @NotBlank
    private String id;

    private String schoolId;

    @NotBlank
    private String name;

    @NotNull
    private CourseType type;

    private Integer studentNum;

    @NotNull
    private Integer price;

}
