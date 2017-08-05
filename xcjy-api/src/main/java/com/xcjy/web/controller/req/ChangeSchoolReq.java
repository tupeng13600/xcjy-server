package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/8/5.
 */
@Data
public class ChangeSchoolReq {

    @NotBlank
    private String studentId;

    @NotBlank
    private String toSchoolId;

    @NotBlank
    private String remark;

}
