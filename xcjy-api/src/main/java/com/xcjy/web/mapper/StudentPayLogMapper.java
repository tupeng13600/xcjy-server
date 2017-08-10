package com.xcjy.web.mapper;

import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.controller.res.PayStatModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StudentPayLogMapper {

    int insert(StudentPayLog record);

    List<StudentPayLog> getByEmployeeId(@Param("employeeId") String employeeId,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);

    List<PayStatModel> getStatModelByEmpIds(@Param("employeeIds") Set<String> employeeIds);
}