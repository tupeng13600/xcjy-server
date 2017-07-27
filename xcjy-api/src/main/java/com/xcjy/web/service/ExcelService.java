package com.xcjy.web.service;

import com.xcjy.web.bean.Student;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.util.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tupeng on 2017/7/25.
 */
@Service
public class ExcelService {

    @Autowired
    private StudentMapper studentMapper;

    public void importStudent(MultipartFile file) throws IOException {
        List<Student> students = getStudent(file);
        if (CollectionUtils.isNotEmpty(students)) {
            studentMapper.insertBatch(students);
        }
    }

    private List<Student> getStudent(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        HSSFSheet sheet = ExcelUtil.getSheet(inputStream);
        List<Map<String, String>> resultList = new ArrayList<>();
        ExcelUtil.getData(resultList, sheet, 2, 3, 15);
        return ExcelUtil.getResultList(resultList, Student.class);
    }

}
