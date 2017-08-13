package com.xcjy.web.mapper;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.bean.CourseStudent;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface CourseStudentMapper {
    int insert(CourseStudent record);

    CourseStudent getBySIdAndCId(@Param("studentId") String studentId, @Param("courseId") String courseId);

    void updateUsedHour(@Param("courseStudent") CourseStudent courseStudent);

    List<CourseStudent> getBySIdAndCIds(@Param("studentIds") Set<String> studentIds, @Param("courseId") String courseId);

    void updateHourBatch(@Param("courseStudents")List<CourseStudent> courseStudents);

    List<CourseStudent> getByStudentIds(@Param("studentIds") List<String> studentIds);
}