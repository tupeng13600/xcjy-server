package com.xcjy.web.mapper;

import com.xcjy.web.bean.Course;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CourseMapper {

    int insert(Course record);

    void update(@Param("course") Course course);

    Course getById(@Param("id") String id);

    List<Course> getAll();

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);
}