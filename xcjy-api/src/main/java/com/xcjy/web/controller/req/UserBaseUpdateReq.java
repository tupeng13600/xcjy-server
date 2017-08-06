package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.UserType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by tupeng on 2017/8/6.
 */
@Data
public class UserBaseUpdateReq {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

}
