package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.StudentPayType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class StudentPayLogStat {

    private String studentId;

    private String studentName;

    private String employeeId;

    private String employeeName;

    private StudentPayType opPayType;

    private Integer money;

    private String remark;

    private Date payTime;

}
