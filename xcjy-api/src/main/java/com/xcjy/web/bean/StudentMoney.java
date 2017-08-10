package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StudentMoney {
    private String id;

    private String schoolId;

    private String studentId;

    private Integer hasPay;

    private Integer hasBack;

    private Integer hasUsed;

    private Integer totalHour;

    private Integer usedHour;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}