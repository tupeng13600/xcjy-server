package com.xcjy.web.controller.res;

import com.xcjy.web.bean.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by tupeng on 2017/8/28.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseTeacherRes extends Employee {

    private Boolean inCourse = false;

}
