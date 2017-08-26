package com.xcjy.web.controller.res;

import com.xcjy.web.bean.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by tupeng on 2017/8/24.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentInRes extends Student {

    private Boolean inCourse;

}
