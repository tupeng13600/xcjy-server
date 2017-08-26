package com.xcjy.web.controller.res;

import com.xcjy.web.common.util.ExcelData;

/**
 * Created by tupeng on 2017/8/7.
 */
public class StudentPaidExcelModel {

    private String schoolId;

    private String employeeId;

    private String studentName;

    private String idCard;

    private Integer hasPay;

    private Integer hasBack;

    private Integer hasUsed;

    @ExcelData(columnName = "咨询师手机号码", parseMethod = "parsePhone2EmployeeId")
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStudentName() {
        return studentName;
    }

    @ExcelData(columnName = "姓名")
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    @ExcelData(columnName = "校区", parseMethod = "schoolParse")
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getIdCard() {
        return idCard;
    }

    @ExcelData(columnName = "身份证号")
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getHasPay() {
        return hasPay;
    }

    @ExcelData(columnName = "缴费金额")
    public void setHasPay(Integer hasPay) {
        this.hasPay = hasPay;
    }

    public Integer getHasBack() {
        return hasBack;
    }

    @ExcelData(columnName = "退费金额")
    public void setHasBack(Integer hasBack) {
        this.hasBack = hasBack;
    }

    public Integer getHasUsed() {
        return hasUsed;
    }

    @ExcelData(columnName = "已使用金额")
    public void setHasUsed(Integer hasUsed) {
        this.hasUsed = hasUsed;
    }
}
