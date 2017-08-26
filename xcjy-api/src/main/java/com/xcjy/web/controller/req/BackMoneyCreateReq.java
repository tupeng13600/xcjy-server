package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.ApplicationStatusType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class BackMoneyCreateReq {

    @NotBlank
    private String studentId;

    @NotNull
    private Integer returnAmount;

    private String remark;

}
