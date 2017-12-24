package com.xcjy.web.mapper;

import com.xcjy.web.bean.CourseTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseTeacherMapper {
    int insert(CourseTeacher record);

    void deleteByCourseId(@Param("schoolId") String schoolId, @Param("courseId") String courseId);

    void deleteByCId(@Param("courseId") String courseId);

    void insertBatch(@Param("courseTeachers") List<CourseTeacher> courseTeachers);

    CourseTeacher getByCIdAndTId(@Param("courseId") String courseId, @Param("employeeId") String employeeId);

    List<CourseTeacher> getByCId(@Param("courseId") String courseId);

    List<CourseTeacher> getByEmployeeId(@Param("employeeId") String employeeId);
}