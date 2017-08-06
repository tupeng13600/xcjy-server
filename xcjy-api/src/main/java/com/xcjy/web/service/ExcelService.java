package com.xcjy.web.service;

import com.xcjy.web.bean.Student;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.common.util.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/25.
 */
@Service
public class ExcelService {

    private static final Integer headerRow = 1;

    private static final Integer dataRowStart = 2;

    private static final Integer maxColumn = 15;

    @Autowired
    private StudentMapper studentMapper;

    public void importStudent(MultipartFile file) throws IOException {
        List<Student> students = getStudent(file);
        if (CollectionUtils.isNotEmpty(students)) {
            List<String> idCardList = students.stream().map(Student::getIdCard).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(studentMapper.getByIdCards(idCardList))){
                throw new EducationException("有身份证号码已经存在");
            }
            studentMapper.insertBatch(students);
        }
    }

    private List<Student> getStudent(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFSheet sheet = ExcelUtil.getSheet(inputStream);
        List<Map<String, String>> resultList = new ArrayList<>();
        ExcelUtil.getData(resultList, sheet, headerRow, dataRowStart, maxColumn);
        List<Student> students = ExcelUtil.getResultList(resultList, Student.class);
        if (CollectionUtils.isNotEmpty(students)) {
            students.forEach(student -> student.setAlreadyPaid(PayStatusType.NO));
        }
        return students;
    }

}
