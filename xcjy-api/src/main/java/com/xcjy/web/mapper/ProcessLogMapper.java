package com.xcjy.web.mapper;

import com.xcjy.web.bean.ProcessLog;
import com.xcjy.web.common.enums.HandlerStatusType;
import org.apache.ibatis.annotations.Param;

public interface ProcessLogMapper {
    int insert(ProcessLog record);

    void update(ProcessLog processLog);

    void updateHandler(@Param("processLog") ProcessLog processLog, @Param("handlerStatus") HandlerStatusType handlerStatus);

    ProcessLog getById(@Param("id") String id);
}