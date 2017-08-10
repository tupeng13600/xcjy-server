package com.xcjy.web.controller.res;

import lombok.Data;

import java.util.List;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class CounselorStatRes {

    private Integer totalMoney = 0;

    private List<StudentPayLogStat> detail;

}
