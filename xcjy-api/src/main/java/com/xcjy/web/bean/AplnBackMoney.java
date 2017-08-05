package com.xcjy.web.bean;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

@Data
public class AplnBackMoney {

    private String id;

    private String schoolId;

    private String studentId;

    private String applicationUserId;

    private Integer returnAmount;

    private ApplicationStatusType applicationStatus;

    private Date applicationTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}