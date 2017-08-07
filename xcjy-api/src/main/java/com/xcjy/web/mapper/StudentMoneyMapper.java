package com.xcjy.web.mapper;

import com.xcjy.web.bean.StudentMoney;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StudentMoneyMapper {
    int insert(StudentMoney record);

    StudentMoney getBySchoolIdAndStudentId(@Param("schoolId") String schoolId, @Param("studentId") String studentId);

    void updateMoney(@Param("studentMoney") StudentMoney studentMoney, @Param("infoTime") Date infoTime);

    void insertBatch(@Param("list") List<StudentMoney> list);
}