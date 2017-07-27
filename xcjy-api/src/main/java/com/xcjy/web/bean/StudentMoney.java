package com.xcjy.web.bean;

import java.util.Date;

public class StudentMoney {
    private String id;

    private String schoolId;

    private String studentId;

    private Integer hasPay;

    private Integer hasBack;

    private Date createTime;

    private Date updateTime;

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

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getHasPay() {
        return hasPay;
    }

    public void setHasPay(Integer hasPay) {
        this.hasPay = hasPay;
    }

    public Integer getHasBack() {
        return hasBack;
    }

    public void setHasBack(Integer hasBack) {
        this.hasBack = hasBack;
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
}