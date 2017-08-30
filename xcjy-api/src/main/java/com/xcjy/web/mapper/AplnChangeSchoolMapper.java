package com.xcjy.web.mapper;

import com.xcjy.web.bean.AplnChangeSchool;
import com.xcjy.web.common.enums.ApplicationStatusType;
import com.xcjy.web.controller.res.AplnSimpleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AplnChangeSchoolMapper {
    int insert(AplnChangeSchool record);

    AplnChangeSchool getById(@Param("id") String id);

    void updateStatus(@Param("changeSchool") AplnChangeSchool aplnChangeSchool, @Param("status") ApplicationStatusType status);

    List<AplnSimpleRes> getSimpleResByIds(@Param("ids") Set<String> ids);

    List<AplnChangeSchool> getByApplicationIds(@Param("userId") String userId);

    List<AplnChangeSchool> getByStudentIds(@Param("studentIds") Set<String> studentIds);

    List<String> getStudentIds(@Param("studentIds") Set<String> studentIds);
}