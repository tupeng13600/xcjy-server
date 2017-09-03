package com.xcjy.web.mapper;

import com.xcjy.web.bean.StudentPayLog;
import com.xcjy.web.common.enums.StudentPayType;
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

    List<PayStatModel> getStatModelByEmpIds(@Param("employeeIds") Set<String> employeeIds, @Param("payType") StudentPayType payType );

    List<PayStatModel> getByEmpIdsAndType(@Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime,
                                          @Param("type") StudentPayType type);

    List<PayStatModel> getStatByIds(@Param("employeeIds") Set<String> employeeIds,
                                    @Param("type") StudentPayType type);

    List<String> getIdsByStartAndEnd(@Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime);

    List<StudentPayLog> getTimeAndEIds(@Param("startTime") Date startTime,
                                          @Param("endTime") Date endTime,
                                          @Param("employeeIds") Set<String> employeeIds,
                                          @Param("payType") StudentPayType payType);

    List<StudentPayLog> getBySchoolId(@Param("schoolId") String schoolId);
}