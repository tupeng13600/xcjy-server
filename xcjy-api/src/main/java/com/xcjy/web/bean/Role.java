package com.xcjy.web.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Role {

    private String id;

    private String schoolId;

    private String name;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}