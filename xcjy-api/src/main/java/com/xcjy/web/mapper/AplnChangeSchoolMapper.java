package com.xcjy.web.mapper;

import com.xcjy.web.bean.AplnChangeSchool;
import com.xcjy.web.common.enums.ApplicationStatusType;
import org.apache.ibatis.annotations.Param;

public interface AplnChangeSchoolMapper {
    int insert(AplnChangeSchool record);

    AplnChangeSchool getById(@Param("id") String id);

    void updateStatus(@Param("changeSchool") AplnChangeSchool aplnChangeSchool, @Param("status") ApplicationStatusType status);
}