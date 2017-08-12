package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by tupeng on 2017/8/12.
 */
@Data
public class GradeUpdateReq {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Integer price;

    private String remark;

}
