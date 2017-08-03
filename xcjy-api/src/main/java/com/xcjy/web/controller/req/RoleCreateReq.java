package com.xcjy.web.controller.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by tupeng on 2017/7/22.
 */
public class RoleCreateReq {

    @NotBlank
    private String roleName;

    private String remark;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
