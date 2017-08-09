package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseStudent;
import org.apache.ibatis.annotations.Param;

public interface CourseStudentMapper {
    int insert(CourseStudent record);

    CourseStudent getBySIdAndCId(@Param("studentId") String studentId, @Param("courseId") String courseId);

    void updateHour(@Param("courseStudent") CourseStudent courseStudent);

}