package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/19.
 */
@Data
public class AplnBackMoneyRes {

    private String id;

    private String schoolId;

    private String schoolName;

    private String studentId;

    private String studentName;

    private String applicationUserId;

    private String applicationUser;

    private Integer returnAmount;

    private ApplicationStatusType applicationStatus;

    private Date applicationTime;

    private String remark;

}
