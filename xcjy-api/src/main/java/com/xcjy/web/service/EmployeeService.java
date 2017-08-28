package com.xcjy.web.service;

import com.xcjy.auth.util.UpcSecurityUtil;
import com.xcjy.web.bean.Employee;
import com.xcjy.web.bean.User;
import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.UserType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.CommonUtil;
import com.xcjy.web.common.util.DateUtil;
import com.xcjy.web.controller.req.EmployeeCreateReq;
import com.xcjy.web.controller.req.EmployeeUpdateReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.mapper.EmployeeMapper;
import com.xcjy.web.mapper.SchoolMapper;
import com.xcjy.web.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    /**
     * 创建employee，user， user默认密码为用户名
     *
     * @param req
     */
    public CreateIdRes create(EmployeeCreateReq req) {
        validateArg(req.getRoleIds(), req.getSchoolId());
        if (null != userMapper.getByPhone(req.getPhone()) || null != userMapper.getByUsername(req.getUsername())) {
            throw new EducationException("用户名或者手机号码已存在");
        }
        if(StringUtils.isNotEmpty(req.getSchoolId())) {
            if(null == schoolMapper.getById(req.getSchoolId())) {
                throw new EducationException("所属校区ID不存在");
            }
        }
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
        user.setRoleId(CommonUtil.getRolIdString(req.getRoleIds()));
        user.setUserType(UserType.EMPLOYEE);
        user.setEntityId(employee.getId());
        user.setLastLoginIp("0.0.0.0");
        user.setLastLoginTime(new Date());
        user.setSalt(UpcSecurityUtil.randomString());
        user.setPassword(UpcSecurityUtil.encryptPwd(req.getUsername(), new SimpleByteSource(user.getSalt())));
        userMapper.insert(user);
        CacheFactory.updateUserCache(user);

        return new CreateIdRes(employee.getId());
    }

    private void validateArg(List<RoleEnum> roleIds, String schoolId) {
        if (roleIds.contains(RoleEnum.CONSULTANT) || roleIds.contains(RoleEnum.CONSULTANT_BOSS)) {
            if (StringUtils.isBlank(schoolId)) {
                throw new EducationException("咨询师创建必须选择校区");
            }
        }
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

        User user = userMapper.getByPhone(req.getPhone());
        if (null != user && !req.getId().equals(user.getEntityId())) {
            throw new EducationException("手机号码已经被使用");
        }

        BeanUtils.copyProperties(req, employee);
        employee.setUpdateTime(new Date());
        employeeMapper.update(employee);
        user = userMapper.getByEntityId(UserType.EMPLOYEE, req.getId());
        if (null == user) {
            logger.warn("更新员工信息接口调用：未查询到员工对应账号信息");
            return;
        }
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setUpdateTime(new Date());
        userMapper.updateBase(user);
        CacheFactory.updateUserCache(user);
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
            logger.warn("删除员工信息接口调用：未查询到员工对应账号信息");
            return;
        }
        userMapper.deleteLogic(user.getId(), new Date());
        CacheFactory.removeUserCache(user);
    }

    /**
     * 获取员工列表
     *
     * @return
     */
    public List<Employee> list() {
        return employeeMapper.getAll();
    }

    public List<Employee> getBySchoolId(String schoolId, RoleEnum role) {
        List<String> employeeIds = userMapper.getBySchoolId(schoolId, role);
        if(CollectionUtils.isEmpty(employeeIds)) {
            return new ArrayList<>();
        }
        return employeeMapper.getByIds(new HashSet<>(employeeIds));
    }

    public List<Employee> getByRole(RoleEnum role) {
        List<User> userList = userMapper.getByRole(role);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(userList)) {
            Set<String> employeeIds = userList.stream().map(User::getEntityId).collect(Collectors.toSet());
            return employeeMapper.getByIds(employeeIds);
        }
        return new ArrayList<>();
    }
}
