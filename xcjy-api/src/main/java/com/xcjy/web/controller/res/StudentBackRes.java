package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/26.
 */
@Data
public class StudentBackRes {

    public String studentId;

    private String studentName;

    private Integer totalMoney;

    private Integer usedMoney;

    private Integer alreadyBackMoney;

    private Integer canBackMoney;

}
