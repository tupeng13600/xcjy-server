package com.xcjy.web.bean;

import com.xcjy.web.common.enums.ProcessLogType;
import lombok.Data;

import java.util.Date;

@Data
public class ProcessLog {

    private String id;

    private String applicationId;

    private ProcessLogType type;

    private String handlerUserId;

    private String handlerStatus;

    private Date handlerTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}