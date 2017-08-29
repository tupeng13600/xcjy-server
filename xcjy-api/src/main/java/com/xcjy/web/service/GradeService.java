package com.xcjy.web.service;

import com.xcjy.web.bean.Grade;
import com.xcjy.auth.util.CurrentThreadLocal;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.controller.req.GradeCreateReq;
import com.xcjy.web.controller.req.GradeUpdateReq;
import com.xcjy.web.controller.res.CreateIdRes;
import com.xcjy.web.mapper.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@Service
public class GradeService {

    @Autowired
    private GradeMapper gradeMapper;


    public CreateIdRes create(GradeCreateReq req) {
        Grade grade = new Grade();
        grade.setSchoolId(CurrentThreadLocal.getSchoolId());
        grade.setName(req.getName());
        grade.setPrice(req.getPrice());
        grade.setRemark(req.getRemark());
        gradeMapper.insert(grade);
        return new CreateIdRes(grade.getId());
    }

    public void update(GradeUpdateReq req) {
        Grade grade = gradeMapper.getById(req.getId());
        if (null == grade) {
            throw new EducationException("年级信息不存在");
        }
        grade.setName(req.getName());
        grade.setPrice(req.getPrice());
        grade.setRemark(grade.getRemark());
        grade.setUpdateTime(new Date());
        gradeMapper.update(grade);
    }

    public void delete(String id) {
        gradeMapper.deleteLogic(id, new Date());
    }

    public List<Grade> getAll() {
        return gradeMapper.getAll();

    }
}
