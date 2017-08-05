package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class StmanagerStudent {
    private String id;

    private String schoolId;

    private String studentId;

    private String employeeId;

    private Integer renewMoney;

    private Integer backMoney;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}