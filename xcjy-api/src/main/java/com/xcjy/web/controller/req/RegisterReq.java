package com.xcjy.web.controller.req;

import lombok.Data;

/**
 * Created by tupeng on 2017/7/18.
 */
@Data
public class RegisterReq {

    private String name;

    private String username;

    private String userType;

    private String entityId;

    private String phone;

    private String password;

    private String roleId;

}
