package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/9/3.
 */
@Data
public class PersonCreateRes extends CreateIdRes {

    public PersonCreateRes(Date birthday) {
        this.birthday = birthday;
    }

    public PersonCreateRes(String id, Date birthday) {
        super(id);
        this.birthday = birthday;
    }

    private Date birthday;

}
