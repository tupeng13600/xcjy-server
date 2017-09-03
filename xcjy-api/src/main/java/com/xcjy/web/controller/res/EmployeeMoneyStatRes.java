package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.List;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class EmployeeMoneyStatRes {

    private Integer totalMoney = 0;

    private Integer totalBack = 0;

    private List<StudentPayLogStat> detail;

}
