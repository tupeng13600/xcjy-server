package com.xcjy.web.controller.res;

import lombok.Data;

/**
 * Created by tupeng on 2017/8/6.
 */
@Data
public class RoleRes {

    private String roleId;

    private String roleName;

    public RoleRes(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
