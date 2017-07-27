package com.xcjy.web.service;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.StudentCreateReq;
import com.xcjy.web.controller.req.StudentUpdateReq;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/7/22.
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;


    public void create(StudentCreateReq req) {
        Student student = new Student();
        BeanUtils.copyProperties(req, student);
        if(null == student.getBirthday()) {
            student.setBirthday(DateUtil.getBirthByIdCard(student.getIdCard()));
        }
        studentMapper.insert(student);
    }

    public void update(StudentUpdateReq req) {
        Student student = studentMapper.getById(req.getId());
        if (null == student) {
            throw new EducationException("学生信息不存在");
        }
        studentMapper.update(student);
    }

    public void deleteLogic(String id) {
        studentMapper.deleteLogic(id, new Date());
    }

    public List<Student> list() {
        return studentMapper.listAll();
    }
}
