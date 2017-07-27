package com.xcjy.web.mapper;

import com.xcjy.web.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int insert(Employee record);

    List<Employee> getAll();

    void update(@Param("employee") Employee employee);

    Employee getById(@Param("id") String id);

    void deleteLogic(@Param("id") String id, @Param("updateTime") Date updateTime);
}