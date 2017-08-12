package com.xcjy.web.mapper;

import com.xcjy.web.bean.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface GradeMapper {
    int insert(Grade record);

    Grade getById(@Param("id") String id);

    void update(Grade grade);

    void deleteLogic(@Param("id") String id, @Param("updateTime")Date updateTime);
}