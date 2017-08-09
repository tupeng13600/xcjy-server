package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseScheduleStudent;
import org.apache.ibatis.annotations.Param;

public interface CourseScheduleStudentMapper {
    int insert(CourseScheduleStudent record);

    void deleteByCSId(@Param("courseScheduleId") String courseScheduleId);
}