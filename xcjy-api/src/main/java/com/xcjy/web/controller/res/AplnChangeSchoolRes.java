package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/19.
 */
@Data
public class AplnChangeSchoolRes {

    private String id;

    private String studentId;

    private String studentName;

    private String fromSchoolId;

    private String fromSchoolName;

    private String toSchoolId;

    private String toSchoolName;

    private String applicationUserId;

    private String applicationUser;

    private ApplicationStatusType applicationStatus;

    private Date applicationTime;

    private String remark;

}
