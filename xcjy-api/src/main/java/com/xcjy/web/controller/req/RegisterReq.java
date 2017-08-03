package com.xcjy.web.controller.req;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/7/18.
 */
@Data
public class RegisterReq {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String userType;

    @NotBlank
    private String entityId;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String roleId;

}
