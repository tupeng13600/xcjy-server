package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * Created by tupeng on 2017/8/9.
 */
@Data
public class CourseTeacherCreateReq {

    @NotBlank
    private String courseId;

    private Set<String> teacherIds;

}
