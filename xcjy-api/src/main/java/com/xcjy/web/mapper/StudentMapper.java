package com.xcjy.web.mapper;

import com.xcjy.web.bean.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StudentMapper {

    int insert(Student record);

    void update(Student student);

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);

    List<Student> listAll();

    Student getById(@Param("id") String id);

    int insertBatch(@Param("students") List<Student> students);

    void updateSchoolId(@Param("student") Student student);
}