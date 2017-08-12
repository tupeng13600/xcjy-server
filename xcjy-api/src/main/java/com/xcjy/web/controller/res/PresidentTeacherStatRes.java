package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.List;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class PresidentTeacherStatRes {

    private Integer totalHour = 0;

    private Integer finishHour = 0;

    private Integer unFinishHour = 0;

    private List<PresidentTeacherStatDetail> details;

}
