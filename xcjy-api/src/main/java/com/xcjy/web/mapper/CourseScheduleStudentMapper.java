package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseScheduleStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CourseScheduleStudentMapper {
    int insert(CourseScheduleStudent record);

    void deleteByCSId(@Param("courseScheduleId") String courseScheduleId);

    void insertBatch(@Param("list") List<CourseScheduleStudent> cssList);

    List<CourseScheduleStudent> getByCourseScheduleId(@Param("courseScheduleId") String courseScheduleId);

    void deleteByIds(@Param("ids") Set<String> ids);

    List<CourseScheduleStudent> getByStudentIds(@Param("studentIds") List<String> studentIds);

    CourseScheduleStudent getById(@Param("id") String id);

    void finish(@Param("css") CourseScheduleStudent courseScheduleStudent);

    CourseScheduleStudent getByCourseScheduleIdAndStudentId(@Param("courseScheduleId") String courseScheduleId, @Param("studentId") String studentId);

    void deleteBySIdAndCSId(@Param("studentId") String studentId, @Param("courseScheduleId")String courseScheduleId);
}