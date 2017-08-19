package com.xcjy.web.mapper;

import com.xcjy.web.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface EmployeeMapper {
    int insert(Employee record);

    List<Employee> getAll();

    void update(@Param("employee") Employee employee);

    Employee getById(@Param("id") String id);

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);

    List<Employee> getByIds(@Param("ids") Set<String> ids);

    List<Employee> getBySchoolId(@Param("schoolId") String schoolId);
}