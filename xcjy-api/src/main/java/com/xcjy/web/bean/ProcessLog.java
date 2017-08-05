package com.xcjy.web.bean;

import com.xcjy.web.common.enums.HandlerStatusType;
import com.xcjy.web.common.enums.ProcessLogType;
import lombok.Data;

import java.util.Date;

@Data
public class ProcessLog {

    private String id;

    private String applicationId;

    private ProcessLogType type;

    private Integer processNum;

    private String handlerUserId;

    private HandlerStatusType handlerStatus;

    private Date handlerTime;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}