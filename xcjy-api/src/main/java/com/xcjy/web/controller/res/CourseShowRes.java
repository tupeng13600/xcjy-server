package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.CourseType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/21.
 */
@Data
public class CourseShowRes {

    private String id;

    private String schoolId;

    private String gradeId;

    private String gradeName;

    private String name;

    private CourseType type;

    private Integer studentNum;

    private Integer price;

    private Integer studyHour;

    private Integer selectedNum;

    private Integer backNum;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}
