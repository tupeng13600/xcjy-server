package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by tupeng on 2017/8/9.
 */
@Data
public class CourseScheduleCreateReq {

    private String courseScheduleId;
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

    @NotEmpty
    private Set<String> studentIds;
}
