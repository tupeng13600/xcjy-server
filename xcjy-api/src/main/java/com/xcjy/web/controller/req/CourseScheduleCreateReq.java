package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tupeng on 2017/8/9.
 */
@Data
public class CourseScheduleCreateReq {

    @NotBlank
    private String courseId;

    @NotBlank
    private String employeeId;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotNull
    private Integer studyTime;

}
