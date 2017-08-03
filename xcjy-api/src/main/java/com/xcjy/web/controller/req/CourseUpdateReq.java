package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.CourseType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class CourseUpdateReq {

    @NotBlank
    private String id;

    @NotBlank
    private String schoolId;

    @NotBlank
    private String name;

    @NotBlank
    private CourseType type;

    private Integer studentNum;

    @NotBlank
    private Integer price;

}
