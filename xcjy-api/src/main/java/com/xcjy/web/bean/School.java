package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class School {
    private String id;

    private String name;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}