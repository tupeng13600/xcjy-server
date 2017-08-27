package com.xcjy.web.mapper;

import com.xcjy.web.bean.StudentMoney;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StudentMoneyMapper {
    int insert(StudentMoney record);

    StudentMoney getBySchoolIdAndStudentId(@Param("schoolId") String schoolId, @Param("studentId") String studentId);

    void updateMoney(@Param("studentMoney") StudentMoney studentMoney);

    void insertBatch(@Param("list") List<StudentMoney> list);

    List<StudentMoney> getByStudentIds(@Param("studentIds") Set<String> studentIds);

    StudentMoney getByStudentId(@Param("studentId") String studentId);

    void decreaseUsedHourBatch(@Param("studentIds") Set<String> studentIds, @Param("studyTime") Integer studyTime, @Param("updateTime") Date updateTime);

    void increaseUsedHour(@Param("studentIds") Set<String> studentIds, @Param("studyTime") Integer studyTime, @Param("updateTime") Date updateTime);

    void deleteLogicByStudentId(@Param("studentId") String studentId, @Param("updateTime") Date updateTime);
}