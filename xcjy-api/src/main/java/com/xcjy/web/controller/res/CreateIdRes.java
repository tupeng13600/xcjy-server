package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class CreateIdRes {

    private String id;

    public CreateIdRes() {
    }

    public CreateIdRes(String id) {
        this.id = id;
    }
}
