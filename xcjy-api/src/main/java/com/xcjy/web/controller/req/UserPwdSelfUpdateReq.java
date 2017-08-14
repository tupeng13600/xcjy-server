package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/8/6.
 */
@Data
public class UserPwdSelfUpdateReq {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;

}
