package com.xcjy.web.mapper;

import com.xcjy.web.bean.StudentMoney;
import org.apache.ibatis.annotations.Param;

public interface StudentMoneyMapper {
    int insert(StudentMoney record);

    StudentMoney getBySchoolIdAndStudentId(@Param("schoolId") String schoolId, @Param("studentId") String studentId);

    void updateMoney(StudentMoney studentMoney);
}