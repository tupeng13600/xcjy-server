package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.RoleEnum;
import com.xcjy.web.common.enums.UserType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tupeng on 2017/7/18.
 */
@Data
public class RegisterReq {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotNull
    private UserType userType;

    private String entityId;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotEmpty
    private List<RoleEnum> roleIds;

}
