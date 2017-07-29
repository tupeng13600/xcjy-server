package com.xcjy.web.service;

import com.xcjy.auth.util.UpcSecurityUtil;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.enums.UserType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.EmployeeCreateReq;
import com.xcjy.web.controller.req.EmployeeUpdateReq;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.UserMapper;
import com.xcjy.web.common.util.DateUtil;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建employee，user， user默认密码为用户名
     *
     * @param req
     */
    public void create(EmployeeCreateReq req) {
        //创建Employee
        Employee employee = new Employee();
        BeanUtils.copyProperties(req, employee);
        if (null == employee.getBirthday()) {
            employee.setBirthday(DateUtil.getBirthByIdCard(employee.getIdCard()));
        }
        employeeMapper.insert(employee);
        //创建user
        User user = new User();
        user.setUsername(req.getUsername());
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setSchoolId(req.getSchoolId());
        user.setRoleId(req.getRoleIds());
        user.setUserType(UserType.EMPLOYEE);
        user.setEntityId(employee.getId());
        user.setLastLoginIp("0.0.0.0");
        user.setLastLoginTime(new Date());
        user.setSalt(UpcSecurityUtil.randomString());
        user.setPassword(UpcSecurityUtil.encryptPwd(req.getUsername(), new SimpleByteSource(user.getSalt())));
        userMapper.insert(user);
    }

    /**
     * 更新employee信息
     *
     * @param req
     */
    @Transactional
    public void update(EmployeeUpdateReq req) {
        Employee employee = employeeMapper.getById(req.getId());
        if (null == employee) {
            throw new EducationException("员工信息不存在");
        }
        BeanUtils.copyProperties(req, employee);
        employee.setUpdateTime(new Date());
        employeeMapper.update(employee);
        User user = userMapper.getByEntityId(UserType.EMPLOYEE, req.getId());
        if (null == user) {
            throw new EducationException("用户账号信息不存在");
        }
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setUpdateTime(new Date());
        userMapper.updateBase(user);
    }

    /**
     * 删除Employee信息
     *
     * @param id
     */
    @Transactional
    public void deleted(String id) {
        Employee employee = employeeMapper.getById(id);
        if (null == employee) {
            throw new EducationException("员工信息不存在");
        }
        employeeMapper.deleteLogic(id, new Date());
        User user = userMapper.getByEntityId(UserType.EMPLOYEE, id);
        if (null == user) {
            throw new EducationException("用户账号信息不存在");
        }
        userMapper.deleteLogic(user.getId(), new Date());
    }

    /**
     * 获取员工列表
     *
     * @return
     */
    public List<Employee> list() {
        return employeeMapper.getAll();
    }

}
