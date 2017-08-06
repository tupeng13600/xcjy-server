package com.xcjy.web.service;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.DateUtil;
import com.xcjy.web.controller.req.StudentCreateReq;
import com.xcjy.web.controller.req.StudentUpdateReq;
import com.xcjy.web.mapper.StudentMapper;
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
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    public void create(StudentCreateReq req) {
        Student student = studentMapper.getByIdCard(req.getIdCard());
        if (null != student) {
            throw new EducationException("学生身份证号已经存在");
        }
        student = new Student();
        BeanUtils.copyProperties(req, student);
        if (null == student.getBirthday()) {
            student.setBirthday(DateUtil.getBirthByIdCard(student.getIdCard()));
        }
        student.setAlreadyPaid(PayStatusType.NO);
        studentMapper.insert(student);
    }

    @Transactional
    public void update(StudentUpdateReq req) {
        Student student = studentMapper.getById(req.getId());
        if (null == student) {
            throw new EducationException("学生信息不存在");
        }
        validateIdCard(req.getIdCard(), student.getIdCard());
        BeanUtils.copyProperties(req, student);
        student.setBirthday(DateUtil.getBirthByIdCard(student.getIdCard()));
        studentMapper.update(student);
    }

    private void validateIdCard(String idCard, String repoIdCard) {
        if (!idCard.equals(repoIdCard)) {
            if (null != studentMapper.getByIdCard(idCard)) {
                throw new EducationException("身份证号码已经被使用");
            }
        }
    }

    public void deleteLogic(String id) {
        studentMapper.deleteLogic(id, new Date());
    }

    public List<Student> list() {
        return studentMapper.listAll();
    }
}
