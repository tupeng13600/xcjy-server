package com.xcjy.web.service;

import com.xcjy.web.bean.CounselorStudent;
import com.xcjy.web.bean.Student;
import com.xcjy.web.bean.StudentMoney;
import com.xcjy.web.common.enums.CounselorStudentStatusType;
import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.exception.EducationException;
import com.xcjy.web.common.util.ExcelUtil;
import com.xcjy.web.controller.res.StudentPaidExcelModel;
import com.xcjy.web.mapper.CounselorStudentMapper;
import com.xcjy.web.mapper.StudentMapper;
import com.xcjy.web.mapper.StudentMoneyMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tupeng on 2017/7/25.
 */
@Service
public class ExcelService {

    private static final Integer headerRow = 1;

    private static final Integer dataRowStart = 2;

    private static final Integer maxStudentColumn = 15;

    private static final Integer maxStudentPaidColumn = 7;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentMoneyMapper studentMoneyMapper;

    @Autowired
    private CounselorStudentMapper counselorStudentMapper;

    public void importStudent(MultipartFile file) throws IOException {
        List<Student> students = getStudent(file);
        if (CollectionUtils.isNotEmpty(students)) {
            Set<String> idCardList = students.stream().map(Student::getIdCard).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(studentMapper.getByIdCards(idCardList))) {
                throw new EducationException("有身份证号码已经存在");
            }
            studentMapper.insertBatch(students);
        }
    }

    private List<Student> getStudent(MultipartFile file) throws IOException {
        List<Student> studentList = getDataList(file, Student.class, headerRow, dataRowStart, maxStudentColumn);
        if (CollectionUtils.isNotEmpty(studentList)) {
            studentList.forEach(student -> {
                student.setAlreadyPaid(PayStatusType.NO);
                student.setDistributionType(DistributionTypeEnum.NO_DISTRIBUTION);
            });
        }
        return studentList;
    }

    private <T> List<T> getDataList(MultipartFile file, Class<T> clss, Integer headerRow,
                                    Integer dataRowStart, Integer maxColumn) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFSheet sheet = ExcelUtil.getSheet(inputStream);
        List<Map<String, String>> resultList = new ArrayList<>();
        ExcelUtil.getData(resultList, sheet, headerRow, dataRowStart, maxColumn);
        return ExcelUtil.getResultList(resultList, clss);
    }

    public void importStudentPaid(MultipartFile file) throws IOException {
        List<StudentPaidExcelModel> excelModels = getDataList(file, StudentPaidExcelModel.class, headerRow, dataRowStart, maxStudentPaidColumn);
        if (CollectionUtils.isNotEmpty(excelModels)) {
            Set<String> idCardList = new HashSet<>();
            for (StudentPaidExcelModel excelModel : excelModels) {
                valid(excelModel);
                idCardList.add(excelModel.getIdCard());
            }
            List<Student> students = studentMapper.getByIdCards(idCardList);
            if (students.size() != idCardList.size()) {
                throw new EducationException("有学生尚未导入基本信息，请确认!!!");
            }
            initData2DB(students, excelModels);
        }
    }

    private void initData2DB(List<Student> students, List<StudentPaidExcelModel> excelModels) {
        List<StudentMoney> studentMonies = new ArrayList<>();
        List<CounselorStudent> counselorStudentList = new ArrayList<>();
        for (Student student : students) {
            StudentMoney studentMoney = new StudentMoney();
            studentMoney.setStudentId(student.getId());
            for (StudentPaidExcelModel excelModel : excelModels) {
                if (excelModel.getIdCard().equals(student.getIdCard())) {
                    BeanUtils.copyProperties(excelModel, studentMoney);
                    break;
                }
                if (StringUtils.isNotBlank(excelModel.getEmployeeId())) {
                    counselorStudentList.add(createCS(student, excelModel));
                }
            }
            studentMonies.add(studentMoney);
        }
        studentMoneyMapper.insertBatch(studentMonies);
        counselorStudentMapper.insertBatch(counselorStudentList);
    }

    private CounselorStudent createCS(Student student, StudentPaidExcelModel excelModel) {
        CounselorStudent counselorStudent = new CounselorStudent();
        counselorStudent.setStudentId(student.getId());
        counselorStudent.setMoney(excelModel.getHasPay());
        counselorStudent.setEmployeeId(excelModel.getEmployeeId());
        counselorStudent.setStatus(CounselorStudentStatusType.HAS_PAY);
        counselorStudent.setSchoolId(excelModel.getSchoolId());
        return counselorStudent;
    }


    private void valid(StudentPaidExcelModel excelModel) {
        if (18 != excelModel.getIdCard().length()) {
            throw new EducationException("学生: " + excelModel.getStudentName() + " 身份证号码不是18位");
        }
        if (excelModel.getHasBack() < 0) {
            throw new EducationException("学生: " + excelModel.getStudentName() + " 的退款金额不能小于 0 ");
        }
        if (excelModel.getHasPay() < 0) {
            throw new EducationException("学生: " + excelModel.getStudentName() + " 的已缴费金额不能小于 0 ");
        }
        if (excelModel.getHasUsed() < 0) {
            throw new EducationException("学生: " + excelModel.getStudentName() + " 的已使用金额不能小于 0 ");
        }
    }


}
