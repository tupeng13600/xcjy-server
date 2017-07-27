package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.CourseType;
import lombok.Data;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class CourseUpdateReq {

    private String id;

    private String schoolId;

    private String name;

    private CourseType type;

    private Integer studentNum;

    private Integer price;

}
