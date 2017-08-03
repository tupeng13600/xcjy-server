package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/7/22.
 */
@Data
public class SchoolUpdateReq {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String remark;

}
