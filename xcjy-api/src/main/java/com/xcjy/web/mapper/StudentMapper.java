package com.xcjy.web.mapper;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.controller.res.StudentInRes;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StudentMapper {

    int insert(Student record);

    void update(Student student);

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);

    Student getById(@Param("id") String id);

    int insertBatch(@Param("students") List<Student> students);

    void updateSchoolId(@Param("student") Student student);

    List<Student> getByIds(@Param("ids") Set<String> ids);

    List<StudentInRes> getInResByIds(@Param("ids") Set<String> ids);

    Student getByIdCard(@Param("idCard") String idCard);

    List<Student> getByIdCards(@Param("idCardList") Set<String> idCardList);

    /**
     * 标记学生的状态为已经分配给咨询师
     * @param studentId
     */
    void updateDisTributeType(@Param("ids") Set<String> studentId,
                           @Param("schoolId") String schoolId,
                           @Param("distributionType") DistributionTypeEnum distributionType,
                           @Param("updateTime") Date updateTime);

    List<Student> getByDisType(@Param("distributionType") DistributionTypeEnum distributionType);

    List<Student> getAll();
}