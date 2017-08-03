package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class SchoolCreateReq {

    @NotBlank
    private String name;

    private String remark;
}
