package com.xcjy.web.controller.res;

import com.xcjy.web.common.enums.DistributionTypeEnum;
import com.xcjy.web.common.enums.PayStatusType;
import lombok.Data;

/**
 * Created by tupeng on 2017/8/15.
 */
@Data
public class FinanceStudentStatRes {

    private String id;

    private String schoolId;

    private String schoolName;

    private String name;

    private String remark;

    private PayStatusType alreadyPaid;

    private DistributionTypeEnum distributionType;

    private Integer hasPay;

    private Integer hasBack;

    private Integer hasUsed;

}
