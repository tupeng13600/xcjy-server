package com.xcjy.web.mapper;

import com.xcjy.web.bean.StmanagerStudent;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StmanagerStudentMapper {
    int insert(StmanagerStudent record);

    List<StmanagerStudent> getBySIdAndScIds(@Param("schoolId") String schoolId, @Param("studentIds") Set<String> studentIds);

    void insertBatch(@Param("list") List<StmanagerStudent> list);

    StmanagerStudent getBySES(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentId") String studentId);

    void updateMoney(@Param("stmanagerStudent") StmanagerStudent stmanagerStudent, @Param("infoTime") Date infoTime);

    List<String> getSIdByEmployeeId(@Param("employeeId") String employeeId);

    void deleteRelation(@Param("ids") List<String> ids, @Param("updateTime") Date updateTime);

    void change(@Param("origin") String origin, @Param("des") String des);

    void deleteRelationByStudentIds(@Param("studentId") String studentId, @Param("updateTime") Date updateTime);
}