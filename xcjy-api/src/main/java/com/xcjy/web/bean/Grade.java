package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Grade {
    private String id;

    private String schoolId;

    private String name;

    private Integer price;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}