package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class ChangeSchoolReq {

    private String studentId;

    private String fromSchoolId;

    private String toSchoolId;

    private String remark;

}
