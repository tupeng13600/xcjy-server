package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class PresidentTeacherStatDetail {

    private String id;

    private String name;

    private String phone;

    private Integer totalHour = 0;

    private Integer finishHour = 0;

    private Integer unFinishHour = 0;

    private String schoolId;

    private String schoolName;


}
