package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class AplnSimpleRes {

    private String id;

    private String fromSchoolId;

    private String toSchoolId;

    private Integer returnAmount;

    private Date applicationTime;

    private ApplicationStatusType applicationStatus;

    private String applicationRemark;

}
