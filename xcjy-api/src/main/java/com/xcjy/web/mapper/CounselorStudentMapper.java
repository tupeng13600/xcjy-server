package com.xcjy.web.mapper;

import com.xcjy.web.bean.CounselorStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface CounselorStudentMapper {
    int insert(CounselorStudent record);

    void insertBatch(@Param("list") List<CounselorStudent> list);

    List<CounselorStudent> getByEIdStuIdScId(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentIds") Set<String> studentIds);

    CounselorStudent getCS(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentId") String studentId);

    List<CounselorStudent> getByStuIdScId(@Param("schoolId") String schoolId, @Param("studentIds") Set<String> studentIds);

    void updateMoney(CounselorStudent counselorStudent);
}