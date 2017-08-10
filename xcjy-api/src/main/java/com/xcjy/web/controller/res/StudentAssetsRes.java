package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.PayStatusType;
import com.xcjy.web.common.enums.SexType;
import lombok.Data;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/10.
 */
@Data
public class StudentAssetsRes {

    private String id;

    private String schoolId;

    private String name;

    private String idCard;

    private SexType sex;

    private String phone;

    private PayStatusType alreadyPaid;

    private Integer hasPay;

    private Integer hasBack;

    private Integer hasUsed;

    private Integer totalHour;

    private Integer usedHour;

}
