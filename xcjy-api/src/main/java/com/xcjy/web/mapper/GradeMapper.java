package com.xcjy.web.mapper;

import com.xcjy.web.bean.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface GradeMapper {
    int insert(Grade record);

    Grade getById(@Param("id") String id);

    void update(Grade grade);

    void deleteLogic(@Param("id") String id, @Param("updateTime")Date updateTime);

    List<Grade> getByIds(@Param("ids") Set<String> ids);

    List<Grade> getAll();

}