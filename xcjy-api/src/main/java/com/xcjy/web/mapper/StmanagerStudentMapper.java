package com.xcjy.web.mapper;

import com.xcjy.web.bean.StmanagerStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface StmanagerStudentMapper {
    int insert(StmanagerStudent record);

    List<StmanagerStudent> getBySIdAndScIds(@Param("schoolId") String schoolId, @Param("studentIds") Set<String> studentIds);

    void insertBatch(@Param("list") List<StmanagerStudent> list);

    StmanagerStudent getSS(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentId") String studentId);

    void updateMoney(StmanagerStudent stmanagerStudent);
}