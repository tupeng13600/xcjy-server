package com.xcjy.web.bean;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;

import java.util.Date;

@Data
public class AplnChangeSchool {

    private String id;

    private String studentId;

    private String fromSchoolId;

    private String toSchoolId;

    private String applicationUserId;

    private ApplicationStatusType applicationStatus;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}