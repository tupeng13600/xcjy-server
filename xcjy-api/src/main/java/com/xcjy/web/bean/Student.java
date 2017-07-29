package com.xcjy.web.bean;

import com.xcjy.web.common.cache.CacheFactory;
import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.SexType;
import com.xcjy.web.common.util.DateUtil;
import com.xcjy.web.common.util.ExcelData;

import java.util.Date;

public class Student {
    private String id;

    private String schoolId;

    private String name;

    private String idCard;

    private SexType sex;

    private String orignSchool;

    private String grade;

    private Date birthday;

    private String subject;

    private String source;

    private String phone;

    private String parentName;

    private String parentSex;

    private String parentIdCard;

    private String parentPhone;

    private String address;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private PayStatusType alreadyPaid;

    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    @ExcelData(columnName = "校区", parseMethod = "schoolParse")
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    @ExcelData(columnName = "姓名")
    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    @ExcelData(columnName = "身份证号")
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public SexType getSex() {
        return sex;
    }

    @ExcelData(columnName = "性别", parseMethod = "sexParse")
    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public String getOrignSchool() {
        return orignSchool;
    }

    @ExcelData(columnName = "学校")
    public void setOrignSchool(String orignSchool) {
        this.orignSchool = orignSchool;
    }

    public String getGrade() {
        return grade;
    }

    @ExcelData(columnName = "年级")
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Date getBirthday() {
        return birthday;
    }

    @ExcelData(columnName = "身份证号", parseMethod = "birthdayPares")
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSubject() {
        return subject;
    }

    @ExcelData(columnName = "学科")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSource() {
        return source;
    }

    @ExcelData(columnName = "来源")
    public void setSource(String source) {
        this.source = source;
    }

    public String getPhone() {
        return phone;
    }

    @ExcelData(columnName = "手机号码")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getParentName() {
        return parentName;
    }

    @ExcelData(columnName = "家长姓名")
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentSex() {
        return parentSex;
    }

    @ExcelData(columnName = "家长性别", parseMethod = "sexParse")
    public void setParentSex(String parentSex) {
        this.parentSex = parentSex;
    }

    public String getParentIdCard() {
        return parentIdCard;
    }

    @ExcelData(columnName = "家长身份证号码")
    public void setParentIdCard(String parentIdCard) {
        this.parentIdCard = parentIdCard;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    @ExcelData(columnName = "家长电话")
    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getAddress() {
        return address;
    }

    @ExcelData(columnName = "家庭住址")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    @ExcelData(columnName = "描述信息")
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public PayStatusType getAlreadyPaid() {
        return alreadyPaid;
    }

    public void setAlreadyPaid(PayStatusType alreadyPaid) {
        this.alreadyPaid = alreadyPaid;
    }

    public SexType sexParse(String sex) {
        SexType[] sexTypes = SexType.values();
        for (SexType sexType : sexTypes) {
            if (sexType.getName().equals(sex.trim())) {
                return sexType;
            }
        }
        return null;
    }

    public String schoolParse(String name) {
        return null == CacheFactory.schools.get(name.trim()) ? null : CacheFactory.schools.get(name.trim()).getId();
    }

    public Date birthdayPares(String idCard) {
        return DateUtil.getBirthByIdCard(idCard);
    }

}