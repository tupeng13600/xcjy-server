package com.xcjy.web.mapper;

import com.xcjy.web.bean.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SchoolMapper {
    int insert(School record);

    School getById(@Param("id") String id);

    void update(@Param("school") School school);

    List<School> getAll();

    List<School> getByIds(@Param("ids") Set<String> ids);
}