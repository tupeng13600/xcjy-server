package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CourseScheduleMapper {
    int insert(CourseSchedule record);

    CourseSchedule getById(@Param("id") String id);

    void updateBase(@Param("schedule") CourseSchedule courseSchedule);

    List<CourseSchedule> listAll();

    void deleteLogic(@Param("id") String id, @Param("updateTime")Date updateTime);
}