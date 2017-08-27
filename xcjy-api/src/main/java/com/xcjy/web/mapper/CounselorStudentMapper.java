package com.xcjy.web.mapper;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.controller.res.CounselorStuNumModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface CounselorStudentMapper {
    int insert(CounselorStudent record);

    void insertBatch(@Param("list") List<CounselorStudent> list);

    List<CounselorStudent> getByEIdStuIdScId(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentIds") Set<String> studentIds);

    CounselorStudent getCS(@Param("schoolId") String schoolId, @Param("employeeId") String employeeId, @Param("studentId") String studentId);

    List<CounselorStudent> getByStudentIds(@Param("studentIds") Set<String> studentIds);

    void updateMoney(CounselorStudent counselorStudent);

    List<CounselorStudent> getByEmployeeId(@Param("employeId") String employeeId);

    List<String> getSIdByEmployeeId(@Param("employeId") String employeeId);

    List<CounselorStudent> getAll();

    List<CounselorStudent> getByTimeAndType(@Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime,
                                            @Param("type") CounselorStudentStatusType type);

    List<CounselorStudent> getByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<CounselorStuNumModel> getStudentNumByEIds(@Param("employeeIds") Set<String> employeeIds);

    CounselorStudent getStuIdAndEmpId(@Param("employeId") String employeeId, @Param("studentId") String studentId);

    void updaStatus(@Param("counselorStudent") CounselorStudent counselorStudent);

    CounselorStudent getByStudentId(@Param("studentId") String studentId);
}