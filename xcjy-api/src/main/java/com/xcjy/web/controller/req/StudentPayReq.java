package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.StudentPayType;
import lombok.Data;

/**
 * Created by tupeng on 2017/7/25.
 */
@Data
public class StudentPayReq {

    private String studentId;

    private String schoolId;

    private String employeeId;

    private StudentPayType payType;

    private Integer money;

    private String remark;

}
