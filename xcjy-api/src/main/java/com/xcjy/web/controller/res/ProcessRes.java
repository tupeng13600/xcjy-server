package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.ApplicationStatusType;
import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class ProcessRes {

    private String id;

    private ProcessLogType type;

    private Integer processNum;

    private HandlerStatusType handlerStatus;

    private Date handlerTime;

    private String schoolName;

    private String studentName;

    private String applicationName;

    private Integer returnAmount;

    private String toSchoolName;

    private ApplicationStatusType applicationStatus;

    private Date applicationTime;

    private String applicationRemark;

}
