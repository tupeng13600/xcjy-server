package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by tupeng on 2017/8/20.
 */
@Data
public class BuyCourseHourReq {

    @NotBlank
    private String courseId;

    @NotNull
    private Integer buyHour;

}
