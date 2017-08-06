package com.xcjy.web.controller.req;

import com.xcjy.web.common.enums.RoleEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by tupeng on 2017/8/6.
 */
@Data
public class UserRoleUpdateReq {

    @NotBlank
    private String id;

    @NotEmpty
    private List<RoleEnum> roleList;

}
